package com.svprdga.infinitescrollsample.di.component

import com.svprdga.infinitescrollsample.di.annotations.PerUiComponent
import com.svprdga.infinitescrollsample.di.module.PresenterModule
import com.svprdga.infinitescrollsample.di.module.UiComponentModule
import com.svprdga.infinitescrollsample.presentation.ui.activity.ListActivity
import dagger.Subcomponent

@PerUiComponent
@Subcomponent(modules = [UiComponentModule::class, PresenterModule::class])
interface UiComponent {

    fun inject(target: ListActivity)
}
