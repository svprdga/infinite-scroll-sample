package com.svprdga.infinitescrollsample.presentation.presenter.abstraction

import com.svprdga.infinitescrollsample.presentation.presenter.view.IFavoritesView

interface IFavoritesPresenter : IPresenter<IFavoritesView> {

    /**
     * The view called his start lifecycle method.
     */
    fun onStart()

}