package com.svprdga.infinitescrollsample.presentation.presenter.view

interface IShowView : IView {

    /**
     * Method that provides simple feedback about an operation in a small popup.
     *
     * @param message Message to be shown.
     */
    fun showSmallPopup(message: String)

    /**
     * Mark the favorite icon as favorite.
     */
    fun setFavoriteIcon()

    /**
     * Mark the favorite icon as not favorite.
     */
    fun setUncheckedFavoriteIcon()

}