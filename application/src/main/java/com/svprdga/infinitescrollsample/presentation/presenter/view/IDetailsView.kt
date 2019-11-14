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

    /**
     * Method that provides simple feedback about an operation in a small popup.
     *
     * @param message Message to be shown.
     */
    fun showSmallPopup(message: String)

    /**
     * Start the view animations.
     */
    fun startAnimations()

}