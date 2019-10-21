package com.svprdga.infinitescrollsample.data.network.rx.transformer

import com.svprdga.infinitescrollsample.data.network.rx.scheduler.ISchedulerProvider
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer

class SingleWorkerTransformer<T>(private val schedulerProvider: ISchedulerProvider) :
    SingleTransformer<T, T> {

    override fun apply(upstream: Single<T>): SingleSource<T> {
        // subscribeOn will cause all upstream calls to run on an io thread.
        // observeOn will cause all the downstream calls to run on the main thread.
        return upstream.subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}
