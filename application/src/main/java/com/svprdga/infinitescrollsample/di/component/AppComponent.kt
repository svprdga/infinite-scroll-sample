package com.svprdga.infinitescrollsample.di.component

import com.svprdga.infinitescrollsample.di.module.*
import com.svprdga.infinitescrollsample.presentation.ui.activity.BaseActivity
import com.svprdga.infinitescrollsample.presentation.ui.application.CoreApp
import com.svprdga.infinitescrollsample.presentation.ui.extra.ShowView
import com.svprdga.infinitescrollsample.presentation.ui.fragment.BaseFragment
//import dagger.Component
//import javax.inject.Singleton

//@Singleton
//@Component(
//    modules = [AppModule::class, UtilModule::class, NetworkModule::class,
//        RepositoryModule::class, RxModule::class, PersistenceModule::class]
//)
//interface AppComponent {
//
//    fun plusUiComponent(
//        presenterModule: PresenterModule
//    ): UiComponent
//
//    fun inject(target: CoreApp)
//    fun inject(target: BaseActivity)
//    fun inject(target: BaseFragment)
//    fun inject(target: ShowView)
//}