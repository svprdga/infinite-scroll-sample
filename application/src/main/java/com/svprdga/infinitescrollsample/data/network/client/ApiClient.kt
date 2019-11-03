package com.svprdga.infinitescrollsample.data.network.client

import com.svprdga.infinitescrollsample.data.network.entity.ErrorEntity
import com.svprdga.infinitescrollsample.data.network.entity.PopularShowsResponse
import com.svprdga.infinitescrollsample.data.network.rx.scheduler.ISchedulerProvider
import com.svprdga.infinitescrollsample.data.network.rx.transformer.SingleWorkerTransformer
import com.svprdga.infinitescrollsample.domain.Mockable
import com.svprdga.infinitescrollsample.domain.exception.KoException
import com.svprdga.infinitescrollsample.util.Logger
import io.reactivex.Single
import io.reactivex.functions.Consumer
import retrofit2.HttpException
import retrofit2.Retrofit

@Mockable
class ApiClient(
    log: Logger,
    private val retrofit: Retrofit,
    private val api: IApi,
    private val apiKey: String,
    private val schedulerProvider: ISchedulerProvider
) {

    // ****************************************** VARS ***************************************** //

    private val errorConsumer = Consumer<Throwable> { throwable ->
        if (throwable is HttpException) {

            log.debug("Converting error $throwable")

            val errorConverter = retrofit.responseBodyConverter<ErrorEntity>(
                ErrorEntity::class.java, arrayOfNulls(0)
            )

            val error = errorConverter.convert(throwable.response()?.errorBody()!!)

            throw KoException(throwable.code(), error?.errors)
        } else {
            throw Exception(throwable)
        }
    }

    // ************************************* PUBLIC METHODS ************************************ //

    fun getPopularShows(page: Int): Single<PopularShowsResponse> {
        return api.getPopularShows(apiKey, "en-US", page)
            .doOnError(errorConsumer)
            .compose(SingleWorkerTransformer<PopularShowsResponse>(schedulerProvider))
    }

}