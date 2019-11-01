package com.svprdga.infinitescrollsample.presentation.presenter.abstraction

import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.view.IFavoritesView

interface IFavoritesPresenter : IPresenter<IFavoritesView> {

    /**
     * The user choosed to unmark the given [show] as favorite.
     */
    fun makeShowNotFavorite(show: Show)

}