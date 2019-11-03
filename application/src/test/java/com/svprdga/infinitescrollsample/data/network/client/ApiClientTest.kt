package com.svprdga.infinitescrollsample.data.network.client

import com.nhaarman.mockitokotlin2.whenever
import com.svprdga.infinitescrollsample.data.network.entity.PopularShowsResponse
import com.svprdga.infinitescrollsample.data.network.rx.scheduler.ISchedulerProvider
import com.svprdga.infinitescrollsample.domain.exception.KoException
import com.svprdga.infinitescrollsample.util.Logger
import de.bechte.junit.runners.context.HierarchicalContextRunner
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Retrofit

private const val API_KEY = "fake_api_key"

@RunWith(HierarchicalContextRunner::class)
class ApiClientTest {

    // *************************************** MOCK VARS *************************************** //

    @Mock
    lateinit var log: Logger
    @Mock
    lateinit var retrofit: Retrofit
    @Mock
    lateinit var api: IApi
    @Mock
    lateinit var schedulerProvider: ISchedulerProvider
    @Mock
    lateinit var response: PopularShowsResponse
    @Mock
    lateinit var httpException: HttpException

    // ****************************************** VARS ***************************************** //

    lateinit var client: ApiClient

    // ***************************************** SET UP **************************************** //

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(schedulerProvider.io()).thenReturn(Schedulers.trampoline())
        whenever(schedulerProvider.ui()).thenReturn(Schedulers.trampoline())

        client = ApiClient(log, retrofit, api, API_KEY, schedulerProvider)
    }

    // ***************************************** TESTS ***************************************** //

    inner class `when calling getPopularShows()` {

        inner class `given OK response` {

            private var single: TestObserver<PopularShowsResponse>? = null

            @Before
            fun setUp() {
                whenever(api.getPopularShows(API_KEY, "en-US", 1))
                    .thenReturn(Single.just(response))

                single = client.getPopularShows(1).test()
            }

            @Test
            fun `should subscribe`() {
                single!!.assertSubscribed()
            }

            @Test
            fun `should return an instance of RecipesResponse`() {
                single!!.assertValue(response)
            }

        }

        inner class `given KO response with a HttpException` {

            private val code = 404
            private val message = "this is the error message"

            @Before
            fun setUp() {
                whenever(httpException.code()).thenReturn(code)
                whenever(httpException.message()).thenReturn(message)
                whenever(api.getPopularShows(API_KEY, "en-US", 1))
                    .thenReturn(Single.error(httpException))
            }

            @Test
            fun `should throw a KoException in a CompositeException`() {
                client.getPopularShows(1).subscribe(
                    object : SingleObserver<PopularShowsResponse> {
                        override fun onSubscribe(d: Disposable) {
                            // Nothing.
                        }

                        override fun onSuccess(t: PopularShowsResponse) {
                            // Test failed.
                            Assert.assertTrue(false)
                        }

                        override fun onError(exception: Throwable) {
                            assert(exception is CompositeException)
                            val compositeException = exception as CompositeException

                            compositeException.exceptions.forEach { innerException ->
                                if (innerException is KoException) {
                                    Assert.assertEquals(code, innerException.statusCode)
                                }
                            }
                        }
                    }
                )
            }

        }

        inner class `given KO response with an Exception` {

            private val exception = Exception()

            @Before
            fun setUp() {
                whenever(api.getPopularShows(API_KEY, "en-US", 1))
                    .thenReturn(Single.error(exception))
            }

            @Test
            fun `should throw two Exceptions in a CompositeException`() {
                client.getPopularShows(1).subscribe(
                    object : SingleObserver<PopularShowsResponse> {
                        override fun onSubscribe(d: Disposable) {
                            // Nothing.
                        }

                        override fun onSuccess(t: PopularShowsResponse) {
                            // Test failed.
                            Assert.assertTrue(false)
                        }

                        override fun onError(exception: Throwable) {
                            assert(exception is CompositeException)
                            val compositeException = exception as CompositeException

                            compositeException.exceptions.forEach { innerException ->
                                Assert.assertTrue(innerException is java.lang.Exception)
                            }
                        }
                    }
                )
            }

        }

    }

}