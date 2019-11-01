package com.svprdga.infinitescrollsample.presentation.ui.application

import android.app.Application
//import com.svprdga.infinitescrollsample.di.component.AppComponent
//import com.svprdga.infinitescrollsample.di.component.DaggerAppComponent
import com.svprdga.infinitescrollsample.di.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CoreApp : Application() {

    // ****************************************** VARS ***************************************** //

//    lateinit var appComponent: AppComponent

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreate() {
        super.onCreate()
//        initDagger()
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

    // ************************************ PRIVATE METHODS ************************************ //

//    /**
//     * Init dagger related components.
//     */
//    private fun initDagger() {
//        appComponent = DaggerAppComponent.builder()
//            .appModule(AppModule(this@CoreApp))
//            .utilModule(UtilModule())
//            .networkModule(NetworkModule())
//            .repositoryModule(RepositoryModule())
//            .build()
//        appComponent.inject(this)
//    }

}