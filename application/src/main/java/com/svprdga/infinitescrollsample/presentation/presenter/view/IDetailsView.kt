package com.svprdga.infinitescrollsample.presentation.presenter.view

interface IDetailsView : IView {

    /**
     * Mark the favorite icon as favorite.
     */
    fun setFavoriteIcon()

    /**
     * Mark the favorite icon as not favorite.
     */
    fun setUncheckedFavoriteIcon()

}