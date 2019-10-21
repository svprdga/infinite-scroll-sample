package com.svprdga.infinitescrollsample.data.network.rx.scheduler

import io.reactivex.Scheduler

interface ISchedulerProvider {

    fun io(): Scheduler

    fun ui(): Scheduler
}