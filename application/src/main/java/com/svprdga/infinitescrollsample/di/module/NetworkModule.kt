package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.BuildConfig
import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.data.network.entity.mapper.Mapper
import com.svprdga.infinitescrollsample.data.network.rx.scheduler.ISchedulerProvider
import com.svprdga.infinitescrollsample.data.network.rx.scheduler.SchedulerProvider
import com.svprdga.infinitescrollsample.domain.API_URL
import com.svprdga.infinitescrollsample.util.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideSchedulerProvider(): ISchedulerProvider {
        return SchedulerProvider()
    }

    @Provides
    @Singleton
    fun provideApiClient(log: Logger, schedulerProvider: ISchedulerProvider): ApiClient {
        return ApiClient(
            log,
            API_URL,
            BuildConfig.IMDB_API_KEY,
            BuildConfig.DEBUG,
            schedulerProvider
        )
    }

    @Provides
    @Singleton
    fun provideMapper(): Mapper {
        return Mapper()
    }

}