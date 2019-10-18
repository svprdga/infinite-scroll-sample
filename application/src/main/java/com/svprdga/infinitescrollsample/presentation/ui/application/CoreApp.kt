package com.svprdga.infinitescrollsample.presentation.ui.application

import android.app.Application
import com.svprdga.infinitescrollsample.di.component.AppComponent
import com.svprdga.infinitescrollsample.di.component.DaggerAppComponent
import com.svprdga.infinitescrollsample.di.module.AppModule
import com.svprdga.infinitescrollsample.di.module.DataModule
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
            .dataModule(DataModule())
            .build()
        appComponent.inject(this)
    }

}