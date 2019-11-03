package com.svprdga.infinitescrollsample.presentation.presenter

import com.nhaarman.mockitokotlin2.*
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.ShowData
import com.svprdga.infinitescrollsample.domain.exception.KoException
import com.svprdga.infinitescrollsample.domain.usecase.ShowsUseCase
import com.svprdga.infinitescrollsample.presentation.presenter.view.IListView
import com.svprdga.infinitescrollsample.util.Logger
import de.bechte.junit.runners.context.HierarchicalContextRunner
import io.reactivex.Single
import io.reactivex.SingleObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(HierarchicalContextRunner::class)
class ListPresenterTest {

    // *************************************** MOCK VARS *************************************** //

    @Mock
    lateinit var log: Logger
    @Mock
    lateinit var showsUseCase: ShowsUseCase
    @Mock
    lateinit var view: IListView
    @Mock
    lateinit var showData: ShowData

    // ****************************************** VARS ***************************************** //

    lateinit var presenter: ListPresenter
    var shows: ArrayList<Show> = arrayListOf()

    val singleSuccess = object : Single<ShowData>() {
        override fun subscribeActual(observer: SingleObserver<in ShowData>) {
            observer.onSuccess(showData)
        }
    }

    val exception = KoException(400, null)
    val singleError = object : Single<ShowData>() {
        override fun subscribeActual(observer: SingleObserver<in ShowData>) {
            observer.onError(exception)
        }
    }

    // ***************************************** SET UP **************************************** //

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        for (i in 1..10) {
            shows.add(
                Show(
                    i, "name_$i",
                    "overview_$i",
                    i.toFloat(),
                    "path_$1",
                    false
                )
            )
        }

        whenever(showData.shows).thenReturn(shows)

        presenter = ListPresenter(log, showsUseCase)
    }

    // ***************************************** TESTS ***************************************** //

    inner class `when calling bind()` {

        @Before
        fun setUp() {
            whenever(showsUseCase.findPopularShows(1)).thenReturn(singleSuccess)
        }

        @Test
        fun `should fetch initial set of items`() {
            presenter.bind(view)
            verify(showsUseCase).findPopularShows(1)
        }

        inner class `given success when fetching content`() {

            @Test
            fun `should display fetched items in view`() {
                presenter.bind(view)
                verify(view).appendShows(showData.shows)
            }

        }

        inner class `given error when fetching content`() {

            @Before
            fun setUp() {
                whenever(showsUseCase.findPopularShows(1)).thenReturn(singleError)
                presenter.bind(view)
            }

            @Test
            fun `should hide the list layout`() {
                verify(view).hideListLayout()
            }

            @Test
            fun `should show the error layout`() {
                verify(view).showErrorLayout()
            }

        }

    }

    inner class `when calling loadNextShowSet` {

        inner class `given no more pages`() {

            @Before
            fun setUp() {
                whenever(showsUseCase.findPopularShows(1)).thenReturn(singleSuccess)
                whenever(showData.isLastPage).thenReturn(true)
                presenter.bind(view)
                presenter.loadNextShowSet()
            }

            @Test
            fun `should do nothing`() {
                verify(showsUseCase, never()).findPopularShows(2)
            }

        }

        inner class `given more pages to load`() {

            @Before
            fun setUp() {
                whenever(showsUseCase.findPopularShows(1)).thenReturn(singleSuccess)
                whenever(showsUseCase.findPopularShows(2)).thenReturn(singleSuccess)
                whenever(showData.isLastPage).thenReturn(false)
                presenter.bind(view)
                presenter.loadNextShowSet()
            }

            @Test
            fun `should fetch new page and display it`() {
                verify(view, times(2)).appendShows(shows)
            }

        }

    }
}
