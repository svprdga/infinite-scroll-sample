package com.svprdga.infinitescrollsample.presentation.presenter.view

import com.svprdga.infinitescrollsample.domain.Show

interface IListView : IView {

    /**
     * Hide all the content of the view.
     */
    fun hideAll()

    /**
     * Shows all the content of the view.
     */
    fun showAll()

    /**
     * Append a [List] of [Show] to the scrollable list.
     */
    fun appendShows(newShows: List<Show>)

    /**
     * Hides the views associated with displaying the list.
     */
    fun hideListLayout()

    /**
     * Shows an error layout to the user.
     *
     * Call this method when an error occurrs in any internal operation to provide
     * a general error feedback.
     */
    fun showErrorLayout()

    /**
     * Hides the error layout.
     */
    fun hideErrorLayout()
}