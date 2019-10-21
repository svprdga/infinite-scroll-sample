package com.svprdga.infinitescrollsample.presentation.presenter.abstraction

import com.svprdga.infinitescrollsample.presentation.presenter.view.IView


/**
 * Basic presenter interface which defines presenter-related methods.
 */
interface IPresenter<T : IView> {

    /**
     * Method to bind a view to this presenter.
     * @param view View to bind.
     */
    fun bind(view: T)

    /**
     * Method which unbinds the bound view from this presenter.
     */
    fun unBind()
}