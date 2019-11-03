package com.svprdga.infinitescrollsample.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.data.network.entity.PopularShowsResponse
import com.svprdga.infinitescrollsample.data.network.entity.mapper.Mapper
import com.svprdga.infinitescrollsample.data.persistence.dao.ShowDao
import com.svprdga.infinitescrollsample.data.persistence.entity.ShowDbEntity
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.ShowData
import com.svprdga.infinitescrollsample.domain.repository.IShowRepository
import de.bechte.junit.runners.context.HierarchicalContextRunner
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.functions.Function
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(HierarchicalContextRunner::class)
class ShowRepositoryTest {

    // *************************************** MOCK VARS *************************************** //

    @Mock
    lateinit var apiClient: ApiClient
    @Mock
    lateinit var mapper: Mapper
    @Mock
    lateinit var showData: ShowData
    @Mock
    lateinit var showDao: ShowDao
    @Mock
    lateinit var show: Show
    @Mock
    lateinit var showDbEntity: ShowDbEntity

    // ****************************************** VARS ***************************************** //

    lateinit var repository: IShowRepository

    lateinit var showDbArray: Array<ShowDbEntity>
    lateinit var showList: List<Show>

    // ***************************************** SET UP **************************************** //

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        showDbArray = arrayOf(showDbEntity)
        showList = listOf(show)

        repository = ShowRepository(apiClient, mapper, showDao)
    }

    // ***************************************** TESTS ***************************************** //

    inner class `when calling findPopularShows()` {

        private var single: TestObserver<ShowData>? = null

        @Before
        fun setUp() {

            val function: Function<PopularShowsResponse, ShowData> = mock()
            val popularShowResponse: PopularShowsResponse = mock()

            whenever(apiClient.getPopularShows(1)).thenReturn(Single.just(popularShowResponse))
            whenever(mapper.showResponseToShowData()).thenReturn(function)
            whenever(function.apply(popularShowResponse)).thenReturn(showData)

            single = repository.findPopularShows(1).test()
        }

        @Test
        fun `should subscribe`() {
            single!!.assertSubscribed()
        }

        @Test
        fun `should return a mapped ShowData`() {
            single!!.assertValue(showData)
        }

    }

    inner class `when calling insertShow()` {

        private var completable: TestObserver<Void>? = null

        @Before
        fun setUp() {

            whenever(mapper.showToShowDbEntity(show)).thenReturn(showDbEntity)
            whenever(showDao.insert(showDbEntity)).thenReturn(Completable.complete())

            completable = repository.insertShow(show).test()
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

    inner class `when calling findAllFavoritesAsync()` {

        private var single: TestObserver<List<Show>>? = null

        @Before
        fun setUp() {

            val function: Function<Array<ShowDbEntity>, List<Show>> = mock()

            whenever(showDao.findAllAsync()).thenReturn(Single.just(showDbArray))
            whenever(mapper.showDbEntitiesToShowsAsync()).thenReturn(function)
            whenever(function.apply(showDbArray)).thenReturn(showList)

            single = repository.findAllFavoritesAsync().test()
        }

        @Test
        fun `should subscribe`() {
            single!!.assertSubscribed()
        }

        @Test
        fun `should return a list of Show`() {
            single!!.assertValue(showList)
        }
    }

    inner class `when calling findAllFavorites()` {

        @Test
        fun `should return list of Show`() {
            whenever(showDao.findAll()).thenReturn(showDbArray)
            whenever(mapper.showDbEntitiesToShows(showDbArray)).thenReturn(showList)
            assertEquals(showList, repository.findAllFavorites())
        }
    }

    inner class `when calling removeFavorite()` {

        private var completable: TestObserver<Void>? = null

        @Before
        fun setUp() {

            whenever(showDao.delete(show)).thenReturn(Completable.complete())

            completable =  repository.removeFavorite(show).test()
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

}