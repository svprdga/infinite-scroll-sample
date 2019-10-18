package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.util.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val LOG_TAG = "application"

@Module
class UtilModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger {
        return Logger(LOG_TAG);
    }

}