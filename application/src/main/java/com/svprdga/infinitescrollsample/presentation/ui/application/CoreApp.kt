package com.svprdga.infinitescrollsample.presentation.ui.application

import android.app.Application
import com.svprdga.infinitescrollsample.di.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CoreApp : Application() {

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CoreApp)
            modules(
                listOf(
                    networkModule,
                    persistenceModule,
                    presenterModule,
                    repositoryModule,
                    rxModule,
                    utilModule
                )
            )
        }
    }

}