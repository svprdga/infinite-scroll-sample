package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.data.repository.ShowRepository
import com.svprdga.infinitescrollsample.di.annotations.PerUiComponent
import com.svprdga.infinitescrollsample.presentation.presenter.ListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.util.Logger
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    @PerUiComponent
    fun provideListPresenter(log: Logger, showRepository: ShowRepository): IListPresenter {
        return ListPresenter(log, showRepository)
    }

}
