package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.presentation.presenter.DetailsPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.FavoritesPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.ListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.ShowPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IDetailsPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IFavoritesPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IShowPresenter
import org.koin.dsl.module

val presenterModule = module {

    factory<IListPresenter> {
        ListPresenter(get(), get())
    }

    factory<IFavoritesPresenter> {
        FavoritesPresenter(get(), get(), get())
    }

    factory<IShowPresenter> {
        ShowPresenter(get(), get(), get(), get())
    }

    factory<IDetailsPresenter> {
        DetailsPresenter(get())
    }
}
