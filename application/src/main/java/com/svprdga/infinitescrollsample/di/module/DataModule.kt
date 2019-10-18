package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.util.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideApiClient(log: Logger): ApiClient {
        return ApiClient(log, "http://test.com", true)
    }

}