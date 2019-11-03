package com.svprdga.infinitescrollsample.presentation.presenter

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.usecase.ShowsUseCase
import com.svprdga.infinitescrollsample.presentation.eventbus.FavoriteEvent
import com.svprdga.infinitescrollsample.presentation.eventbus.FavoritesBus
import com.svprdga.infinitescrollsample.presentation.presenter.view.IShowView
import com.svprdga.infinitescrollsample.util.Logger
import com.svprdga.infinitescrollsample.util.TextProvider
import de.bechte.junit.runners.context.HierarchicalContextRunner
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.Exception

@RunWith(HierarchicalContextRunner::class)
class ShowPresenterTest {

    // *************************************** MOCK VARS *************************************** //

    @Mock
    lateinit var log: Logger
    @Mock
    lateinit var showsUseCase: ShowsUseCase
    @Mock
    lateinit var textProvider: TextProvider
    @Mock
    lateinit var favoritesBus: FavoritesBus
    @Mock
    lateinit var view: IShowView

    // ****************************************** VARS ***************************************** //

    lateinit var presenter: ShowPresenter
    val show = Show(
        22,
        "test_show",
        "overview",
        0.54F,
        "path",
        true
    )

    val completableSuccess = object : Completable() {
        override fun subscribeActual(observer: CompletableObserver) {
            observer.onComplete()
        }
    }

    val exception = Exception("error_message")
    val completableError = object : Completable() {
        override fun subscribeActual(observer: CompletableObserver) {
            observer.onError(exception)
        }
    }

    val layoutPosition = 26
    val unexpectedError = "unexpectedError"
    val showAddedFavorites = "showAddedFavorites"
    val showRemovedFavorites = "showRemovedFavorites"

    // ***************************************** SET UP **************************************** //

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(textProvider.unexpectedError).thenReturn(unexpectedError)
        whenever(textProvider.showAddedFavorites).thenReturn(showAddedFavorites)
        whenever(textProvider.showRemovedFavorites).thenReturn(showRemovedFavorites)

        presenter = ShowPresenter(log, showsUseCase, textProvider, favoritesBus)
    }

    // ***************************************** TESTS ***************************************** //

    inner class `when calling favoriteButtonClick()` {

        inner class `given remove from favorites` {

            inner class `given error` {

                @Before
                fun setUp() {
                    show.isFavorite = true
                    presenter.show = show
                    presenter.bind(view)
                    whenever(showsUseCase.removeFavorite(show))
                        .thenReturn(completableError)
                    presenter.favoriteButtonClick(layoutPosition)
                }

                @Test
                fun `should log error`() {
                    verify(log).error(any())
                }

                @Test
                fun `should show unexpected error message in view`() {
                    verify(view).showSmallPopup(unexpectedError)
                }
            }

            inner class `given success result` {

                @Before
                fun setUp() {
                    show.isFavorite = true
                    presenter.show = show
                    presenter.bind(view)
                    whenever(showsUseCase.removeFavorite(show))
                        .thenReturn(completableSuccess)
                    presenter.favoriteButtonClick(layoutPosition)
                }

                @Test
                fun `should show info message in view`() {
                    verify(view).showSmallPopup(showRemovedFavorites)
                }

                @Test
                fun `should set as not favorite in view`() {
                    verify(view).setUncheckedFavoriteIcon()
                }

                @Test
                fun `should emit FavoriteEvent`() {
                    verify(favoritesBus).setFavoriteEvent(eq(FavoriteEvent(show, layoutPosition)))
                }

            }
        }

        inner class `given add to favorites` {

            inner class `given error` {

                @Before
                fun setUp() {
                    show.isFavorite = false
                    presenter.show = show
                    presenter.bind(view)
                    whenever(showsUseCase.insertShow(show))
                        .thenReturn(completableError)
                    presenter.favoriteButtonClick(layoutPosition)
                }

                @Test
                fun `should log error`() {
                    verify(log).error(any())
                }

                @Test
                fun `should show unexpected error message in view`() {
                    verify(view).showSmallPopup(unexpectedError)
                }
            }

            inner class `given success result` {

                @Before
                fun setUp() {
                    show.isFavorite = false
                    presenter.show = show
                    presenter.bind(view)
                    whenever(showsUseCase.insertShow(show))
                        .thenReturn(completableSuccess)
                    presenter.favoriteButtonClick(layoutPosition)
                }

                @Test
                fun `should show info message in view`() {
                    verify(view).showSmallPopup(showAddedFavorites)
                }

                @Test
                fun `should set as favorite in view`() {
                    verify(view).setFavoriteIcon()
                }

                @Test
                fun `should emit FavoriteEvent`() {
                    verify(favoritesBus).setFavoriteEvent(eq(FavoriteEvent(show, layoutPosition)))
                }

            }

        }

    }
}