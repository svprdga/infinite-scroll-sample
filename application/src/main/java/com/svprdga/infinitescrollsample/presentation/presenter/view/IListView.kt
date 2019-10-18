package com.svprdga.infinitescrollsample.presentation.presenter.view

interface IListView : IView {

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