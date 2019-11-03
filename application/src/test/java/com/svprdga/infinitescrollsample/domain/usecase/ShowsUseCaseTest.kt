package com.svprdga.infinitescrollsample.domain.usecase

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.ShowData
import com.svprdga.infinitescrollsample.domain.repository.IShowRepository
import de.bechte.junit.runners.context.HierarchicalContextRunner
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val TOTAL_ITEMS = 5
private const val FAVORITE_EACH = 2

@RunWith(HierarchicalContextRunner::class)
class ShowsUseCaseTest {

    // *************************************** MOCK VARS *************************************** //

    @Mock
    lateinit var showRepository: IShowRepository

    // ****************************************** VARS ***************************************** //

    lateinit var showData: ShowData
    lateinit var useCase: ShowsUseCase
    lateinit var shows: ArrayList<Show>
    lateinit var favorites: ArrayList<Show>

    // ***************************************** SET UP **************************************** //

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        initializeTestVars()

        useCase = ShowsUseCase(showRepository)
    }

    // ***************************************** TESTS ***************************************** //

    inner class `when calling findPopularShows()` {

        private lateinit var single: TestObserver<ShowData>

        @Before
        fun setUp() {
            whenever(showRepository.findAllFavorites()).thenReturn(favorites)
            whenever(showRepository.findPopularShows(1))
                .thenReturn(Single.just(showData))

            single = useCase.findPopularShows(1).test()
        }

        @After
        fun finalize() {
            single.dispose()
        }

        @Test
        fun `should subscribe`() {
            single.assertSubscribed()
        }

        @Test
        fun `should call IShowRepository#findPopularShows()`() {
            verify(showRepository).findPopularShows(1)
        }

        @Test
        fun `should mark favorites as favorites`() {
            single.assertValueAt(0) {
                var assert = false

                for (i in 0..TOTAL_ITEMS) {

                    val shouldBeFavorite = i % FAVORITE_EACH == 0

                    assert = shouldBeFavorite == it.shows[i].isFavorite
                    if (!assert) break
                }

                assert
            }
        }

    }

    inner class `when calling insertShow`() {

        private lateinit var completable: TestObserver<Void>
        private lateinit var show: Show

        @Before
        fun setUp() {
            show = shows[0]
            whenever(showRepository.insertShow(show)).thenReturn(Completable.complete())

            completable = useCase.insertShow(show).test()
        }

        @After
        fun finalize() {
            completable.dispose()
        }

        @Test
        fun `should subscribe`() {
            completable.assertSubscribed()
        }

        @Test
        fun `should complete`() {
            completable.assertComplete()
        }

    }

    inner class `when calling findAllFavoritesAsync`() {

        private lateinit var single: TestObserver<List<Show>>

        @Before
        fun setUp() {
            whenever(showRepository.findAllFavoritesAsync())
                .thenReturn(Single.just(shows))

            single = useCase.findAllFavoritesAsync().test()
        }

        @After
        fun finalize() {
            single.dispose()
        }

        @Test
        fun `should subscribe`() {
            single.assertSubscribed()
        }

        @Test
        fun `should return a list of Show`() {
            single.assertValue(shows)
        }

    }

    inner class `when calling findAllFavorites()` {

        @Test
        fun `should return a list of Show`() {
            whenever(showRepository.findAllFavorites()).thenReturn(shows)
            assertEquals(shows, useCase.findAllFavorites())
        }

    }

    inner class `when calling removeFavorite()` {

        private lateinit var completable: TestObserver<Void>
        private lateinit var show: Show

        @Before
        fun setUp() {
            show = shows[0]
            whenever(showRepository.removeFavorite(show)).thenReturn(Completable.complete())

            completable = useCase.removeFavorite(show).test()
        }

        @After
        fun finalize() {
            completable.dispose()
        }

        @Test
        fun `should subscribe`() {
            completable.assertSubscribed()
        }

        @Test
        fun `should complete`() {
            completable.assertComplete()
        }

    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun initializeTestVars() {
        shows = arrayListOf()
        favorites = arrayListOf()

        for (i in 0..TOTAL_ITEMS) {

            val show = Show(
                i,
                "name_$i",
                "overview_$i",
                100 - i.toFloat(),
                "path_$i",
                false
            )
            shows.add(show)

            val isFavorite = i % FAVORITE_EACH == 0

            if (isFavorite) {
                show.isFavorite = true
                favorites.add(show)
            }
        }

        showData = ShowData(1, false, shows)
    }

}