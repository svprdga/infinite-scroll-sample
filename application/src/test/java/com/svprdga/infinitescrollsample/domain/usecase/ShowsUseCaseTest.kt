package com.svprdga.infinitescrollsample.domain.usecase

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.ShowData
import com.svprdga.infinitescrollsample.domain.repository.IShowRepository
import de.bechte.junit.runners.context.HierarchicalContextRunner
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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

        private var single: TestObserver<ShowData>? = null

        @Before
        fun setUp() {
            whenever(showRepository.findPopularShows(1))
                .thenReturn(Single.just(showData))

            single = useCase.findPopularShows(1).test()
        }

        @Test
        fun `should subscribe`() {
            single!!.assertSubscribed()
        }

        @Test
        fun `should call IShowRepository#findPopularShows()`() {
            verify(showRepository).findPopularShows(1)
        }

        @Test
        fun `should mark favorites as favorites`() {
            useCase.findPopularShows(1)
                .subscribe(object : SingleObserver<ShowData> {
                    override fun onSubscribe(d: Disposable) {
                        // Nothing.
                    }

                    override fun onError(e: Throwable) {
                        // Test failed.
                        assertTrue(false)
                    }

                    override fun onSuccess(data: ShowData) {

                        for (i in 0..TOTAL_ITEMS) {

                            val shouldBeFavorite = i % FAVORITE_EACH == 0

                            assertEquals(shouldBeFavorite, data.shows[i].isFavorite)
                        }
                    }
                })
        }

    }

    inner class `when calling insertShow`() {

        private var completable: TestObserver<Void>? = null
        private lateinit var show: Show

        @Before
        fun setUp() {
            show = shows[0]
            whenever(showRepository.insertShow(show)).thenReturn(Completable.complete())

            completable = useCase.insertShow(show).test()
        }

        @Test
        fun `should subscribe`() {
            completable!!.assertSubscribed()
        }

        @Test
        fun `should complete`() {
            completable!!.assertComplete()
        }

    }

    inner class `when calling findAllFavoritesAsync`() {

        private var single: TestObserver<List<Show>>? = null

        @Before
        fun setUp() {
            whenever(showRepository.findAllFavoritesAsync())
                .thenReturn(Single.just(shows))

            single = useCase.findAllFavoritesAsync().test()
        }

        @Test
        fun `should subscribe`() {
            single!!.assertSubscribed()
        }

        @Test
        fun `should return a list of Show`() {
            single!!.assertValue(shows)
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

        private var completable: TestObserver<Void>? = null
        private lateinit var show: Show

        @Before
        fun setUp() {
            show = shows[0]
            whenever(showRepository.removeFavorite(show)).thenReturn(Completable.complete())

            completable = useCase.removeFavorite(show).test()
        }

        @Test
        fun `should subscribe`() {
            completable!!.assertSubscribed()
        }

        @Test
        fun `should complete`() {
            completable!!.assertComplete()
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