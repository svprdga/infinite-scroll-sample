package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.data.network.rx.scheduler.ISchedulerProvider
import com.svprdga.infinitescrollsample.data.network.rx.scheduler.SchedulerProvider
import com.svprdga.infinitescrollsample.presentation.eventbus.FragmentNavBus
//import dagger.Module
//import dagger.Provides
import org.koin.dsl.module
//import javax.inject.Singleton

//@Module
//class RxModule {
//
//    @Provides
//    @Singleton
//    fun provideSchedulerProvider(): ISchedulerProvider {
//        return SchedulerProvider()
//    }
//
//    @Provides
//    @Singleton
//    fun provideAppFragmentBus(): FragmentNavBus {
//        return FragmentNavBus()
//    }
//
//}

val rxModule = module {

    single<ISchedulerProvider> {
        SchedulerProvider()
    }

    single {
        FragmentNavBus()
    }
}