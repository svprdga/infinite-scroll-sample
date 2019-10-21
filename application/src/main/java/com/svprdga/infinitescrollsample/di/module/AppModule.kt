package com.svprdga.infinitescrollsample.di.module

import android.app.Application
import android.content.Context
import com.svprdga.infinitescrollsample.presentation.ui.application.CoreApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: CoreApp) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return app
    }

    @Provides
    @Singleton
    fun provideCoreApp(): CoreApp {
        return app
    }

}