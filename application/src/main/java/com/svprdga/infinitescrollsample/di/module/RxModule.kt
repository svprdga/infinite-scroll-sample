package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.data.network.rx.scheduler.ISchedulerProvider
import com.svprdga.infinitescrollsample.data.network.rx.scheduler.SchedulerProvider
import com.svprdga.infinitescrollsample.presentation.eventbus.FavoritesBus
import com.svprdga.infinitescrollsample.presentation.eventbus.FragmentNavBus
import org.koin.dsl.module

val rxModule = module {

    single<ISchedulerProvider> {
        SchedulerProvider()
    }

    single {
        FragmentNavBus()
    }

    single {
        FavoritesBus()
    }
}