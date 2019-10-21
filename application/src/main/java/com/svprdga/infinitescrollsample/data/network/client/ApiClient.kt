package com.svprdga.infinitescrollsample.data.network.client

import com.svprdga.infinitescrollsample.data.network.entity.ErrorEntity
import com.svprdga.infinitescrollsample.data.network.entity.PopularShowsResponse
import com.svprdga.infinitescrollsample.data.network.rx.scheduler.ISchedulerProvider
import com.svprdga.infinitescrollsample.data.network.rx.scheduler.SchedulerProvider
import com.svprdga.infinitescrollsample.data.network.rx.transformer.SingleWorkerTransformer
import com.svprdga.infinitescrollsample.domain.Mockable
import com.svprdga.infinitescrollsample.domain.exception.KoException
import com.svprdga.infinitescrollsample.util.Logger
import io.reactivex.Single
import io.reactivex.functions.Consumer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIMEOUT = 5000L

@Singleton
@Mockable
class ApiClient(
    log: Logger,
    url: String,
    private val apiKey: String,
    enableLog: Boolean = false,
    private val schedulerProvider: ISchedulerProvider
) {

    // ****************************************** VARS ***************************************** //

    private lateinit var retrofit: Retrofit
    private val api: IApi

    private val errorConsumer = Consumer<Throwable> { throwable ->
        if (throwable is HttpException) {

            log.debug("Converting error $throwable")

            val errorConverter = retrofit.responseBodyConverter<ErrorEntity>(
                ErrorEntity::class.java, arrayOfNulls(0)
            )

            val error = errorConverter.convert(throwable.response()?.errorBody())

            throw KoException(throwable.code(), error?.errors)
        } else {
            throw Exception(throwable)
        }
    }

    // ************************************** CONSTRUCTORS ************************************* //

    init {

        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        httpClientBuilder.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        httpClientBuilder.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)

        if (enableLog) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(logging)
        }
        val builder = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        retrofit = builder.client(httpClientBuilder.build()).build()
        api = retrofit.create(IApi::class.java)
    }

    // ************************************* PUBLIC METHODS ************************************ //

    fun getPopularShows(page: Int): Single<PopularShowsResponse> {
        return api.getPopularShows(apiKey, "en-US", page)
            .doOnError(errorConsumer)
            .compose(SingleWorkerTransformer<PopularShowsResponse>(schedulerProvider))
    }

}