package com.svprdga.infinitescrollsample.presentation.presenter.abstraction

import com.svprdga.infinitescrollsample.presentation.presenter.view.IListView

interface IListPresenter : IPresenter<IListView> {

    /**
     * The user is scrolling down. Try to fetch the next page of results if exists.
     */
    fun loadNextShowSet()

}