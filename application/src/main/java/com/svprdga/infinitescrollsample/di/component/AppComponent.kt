package com.svprdga.infinitescrollsample.di.component

import com.svprdga.infinitescrollsample.di.module.AppModule
import com.svprdga.infinitescrollsample.di.module.DataModule
import com.svprdga.infinitescrollsample.di.module.PresenterModule
import com.svprdga.infinitescrollsample.di.module.UtilModule
import com.svprdga.infinitescrollsample.presentation.ui.activity.BaseActivity
import com.svprdga.infinitescrollsample.presentation.ui.application.CoreApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, UtilModule::class, DataModule::class])
interface AppComponent {

    fun plusUiComponent(
            presenterModule: PresenterModule
    ): UiComponent

    fun inject(target: CoreApp)
    fun inject(target: BaseActivity)
}