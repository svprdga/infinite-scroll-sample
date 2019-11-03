package com.svprdga.infinitescrollsample.presentation.presenter

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.ShowData
import com.svprdga.infinitescrollsample.domain.exception.KoException
import com.svprdga.infinitescrollsample.domain.usecase.ShowsUseCase
import com.svprdga.infinitescrollsample.presentation.eventbus.FavoriteEvent
import com.svprdga.infinitescrollsample.presentation.eventbus.FavoritesBus
import com.svprdga.infinitescrollsample.presentation.presenter.view.IFavoritesView
import com.svprdga.infinitescrollsample.util.Logger
import de.bechte.junit.runners.context.HierarchicalContextRunner
import io.reactivex.Single
import io.reactivex.SingleObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val TOTAL_ITEMS = 5

@RunWith(HierarchicalContextRunner::class)
class FavoritesPresenterTest {

    // *************************************** MOCK VARS *************************************** //

    @Mock
    lateinit var log: Logger
    @Mock
    lateinit var showsUseCase: ShowsUseCase
    @Mock
    lateinit var view: IFavoritesView

    // ****************************************** VARS ***************************************** //

    lateinit var presenter: FavoritesPresenter
    var shows: ArrayList<Show> = arrayListOf()

    val singleSuccess = object : Single<List<Show>>() {
        override fun subscribeActual(observer: SingleObserver<in List<Show>>) {
            observer.onSuccess(shows)
        }
    }

    val singleSuccessOneShow = object : Single<List<Show>>() {
        override fun subscribeActual(observer: SingleObserver<in List<Show>>) {
            observer.onSuccess(listOf(shows[0]))
        }
    }

    val singleSuccessEmpty = object : Single<List<Show>>() {
        override fun subscribeActual(observer: SingleObserver<in List<Show>>) {
            observer.onSuccess(arrayListOf())
        }
    }

    val exception = KoException(400, null)
    val singleError = object : Single<List<Show>>() {
        override fun subscribeActual(observer: SingleObserver<in List<Show>>) {
            observer.onError(exception)
        }
    }

    val favoritesBus = FavoritesBus()

    // ***************************************** SET UP **************************************** //

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        for (i in 0..TOTAL_ITEMS) {
            shows.add(
                Show(
                    i, "name_$i", "overview_$i",
                    100F - i.toFloat(), "path_$i", true
                )
            )
        }

        whenever(showsUseCase.findAllFavoritesAsync()).thenReturn(singleSuccess)

        presenter = FavoritesPresenter(log, showsUseCase, favoritesBus)
    }

    // ***************************************** TESTS ***************************************** //

    inner class `when calling bind()` {

        @Test
        fun `should fetch initial set of items`() {
            whenever(showsUseCase.findAllFavoritesAsync()).thenReturn(singleSuccess)
            presenter.bind(view)
            verify(showsUseCase).findAllFavoritesAsync()
        }

        inner class `given success with an empty array`() {

            @Before
            fun setUp() {
                whenever(showsUseCase.findAllFavoritesAsync()).thenReturn(singleSuccessEmpty)
            }

            @Test
            fun `should show empty favorites layout in view`() {
                presenter.bind(view)
                verify(view).showEmptyFavoritesLayout()
            }
        }

        inner class `given success with a list of Show`() {

            @Before
            fun setUp() {
                presenter.bind(view)
            }

            @Test
            fun `should hide empty favorites layout in view`() {
                verify(view).hideEmptyFavoritesLayout()
            }

            @Test
            fun `should display fetched shows in view`() {
                verify(view).setFavorites(shows)
            }
        }

        inner class `given error when fetching content`() {

            @Before
            fun setUp() {
                whenever(showsUseCase.findAllFavoritesAsync()).thenReturn(singleError)
                presenter.bind(view)
            }

            @Test
            fun `should log error`() {
                verify(log).error(any())
            }
        }
    }

    inner class `when receiving a FavoriteEvent` {

        inner class `given a Show is no longer a favorite` {

            lateinit var show: Show
            val layoutPosition = 2

            @Before
            fun setUp() {
                show = shows[0]

                show.isFavorite = false
                presenter.bind(view)
                favoritesBus.setFavoriteEvent(FavoriteEvent(show, layoutPosition))
            }

            @Test
            fun `should remove Show from the list in view`() {
                verify(view).removeShowFromList(layoutPosition)
            }

        }

        inner class `given last Show to be removed from favorites` {

            lateinit var show: Show
            val layoutPosition = 0

            @Before
            fun setUp() {
                show = shows[0]
                whenever(showsUseCase.findAllFavoritesAsync()).thenReturn(singleSuccessOneShow)

                show.isFavorite = false
                presenter.bind(view)
                favoritesBus.setFavoriteEvent(FavoriteEvent(show, layoutPosition))
            }

            @Test
            fun `should remove Show from the list in view`() {
                verify(view).removeShowFromList(layoutPosition)
            }

            @Test
            fun `should show empty favorites layout in view`() {
                verify(view).showEmptyFavoritesLayout()
            }

        }

    }

}