package com.svprdga.infinitescrollsample.presentation.presenter.view

import com.svprdga.infinitescrollsample.domain.Show

interface IFavoritesView : IView {

    /**
     * Populate the list of favorite [Show].
     */
    fun setFavorites(shows: List<Show>)

    /**
     * Hide the list of favorites.
     */
    fun hideFavoritesList()

    /**
     * Show the layout which informs the user that they don't have any favorites yet.
     */
    fun showEmptyFavoritesLayout()

    /**
     * Hide the no-favorites layout.
     */
    fun hideEmptyFavoritesLayout()

    /**
     * Method that provides simple feedback about an operation in a small popup.
     *
     * @param message Message to be shown.
     */
    fun showSmallPopup(message: String)

    /**
     * Remove from the list the show that was selected by the user to be unmarked as favorite.
     */
    fun removeShowFromList(position: Int)

}