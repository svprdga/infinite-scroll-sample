package com.svprdga.infinitescrollsample.di.module

//import com.svprdga.infinitescrollsample.di.annotations.PerUiComponent
import com.svprdga.infinitescrollsample.domain.repository.IShowRepository
import com.svprdga.infinitescrollsample.presentation.eventbus.FragmentNavBus
import com.svprdga.infinitescrollsample.presentation.presenter.FavoritesPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.ListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.MainPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.ShowPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IFavoritesPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IMainPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IShowPresenter
import com.svprdga.infinitescrollsample.util.Logger
import com.svprdga.infinitescrollsample.util.TextProvider
//import dagger.Module
//import dagger.Provides
import org.koin.dsl.module

//@Module
//class PresenterModule {
//
//    @Provides
//    @PerUiComponent
//    fun provideMainPresenter(fragmentNavBus: FragmentNavBus): IMainPresenter {
//        return MainPresenter(fragmentNavBus)
//    }
//
//    @Provides
//    @PerUiComponent
//    fun provideListPresenter(
//        log: Logger, showRepository: IShowRepository,
//        navBus: FragmentNavBus
//    ): IListPresenter {
//        return ListPresenter(log, showRepository, navBus)
//    }
//
//    @Provides
//    @PerUiComponent
//    fun provideFavoritesPresenter(
//        log: Logger, showRepository: IShowRepository,
//        textProvider: TextProvider, navBus: FragmentNavBus
//    ): IFavoritesPresenter {
//        return FavoritesPresenter(log, showRepository, textProvider, navBus)
//    }
//
//    @Provides
//    @PerUiComponent
//    fun provideShowPresenter(
//        showRepository: IShowRepository
//    ): IShowPresenter {
//        return ShowPresenter(showRepository)
//    }
//
//}

val presenterModule = module {

    factory<IMainPresenter> {
        MainPresenter(get())
    }

    factory<IListPresenter> {
        ListPresenter(get(), get(), get())
    }

    factory<IFavoritesPresenter> {
        FavoritesPresenter(get(), get(), get(), get())
    }

    factory<IShowPresenter> {
        ShowPresenter(get())
    }
}
