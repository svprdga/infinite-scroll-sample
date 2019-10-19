package com.svprdga.infinitescrollsample.presentation.ui.application

import android.app.Application
import com.svprdga.infinitescrollsample.di.component.AppComponent
import com.svprdga.infinitescrollsample.di.component.DaggerAppComponent
import com.svprdga.infinitescrollsample.di.module.AppModule
import com.svprdga.infinitescrollsample.di.module.NetworkModule
import com.svprdga.infinitescrollsample.di.module.RepositoryModule
import com.svprdga.infinitescrollsample.di.module.UtilModule

class CoreApp : Application() {

    // ****************************************** VARS ***************************************** //

    lateinit var appComponent: AppComponent

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    // ************************************ PRIVATE METHODS ************************************ //

    /**
     * Init dagger related components.
     */
    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this@CoreApp))
            .utilModule(UtilModule())
            .networkModule(NetworkModule())
            .repositoryModule(RepositoryModule())
            .build()
        appComponent.inject(this)
    }

}