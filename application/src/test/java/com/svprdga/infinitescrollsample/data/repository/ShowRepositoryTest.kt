package com.svprdga.infinitescrollsample.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.data.network.entity.PopularShowsResponse
import com.svprdga.infinitescrollsample.data.network.entity.mapper.Mapper
import com.svprdga.infinitescrollsample.domain.ShowData
import de.bechte.junit.runners.context.HierarchicalContextRunner
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.functions.Function
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

    // ****************************************** VARS ***************************************** //

    lateinit var repository: ShowRepository

    // ***************************************** SET UP **************************************** //

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        repository = ShowRepository(apiClient, mapper)
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

}