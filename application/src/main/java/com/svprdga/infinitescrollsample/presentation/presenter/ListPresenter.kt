package com.svprdga.infinitescrollsample.presentation.presenter

import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.di.annotations.PerUiComponent
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IListView
import com.svprdga.infinitescrollsample.util.Logger

@PerUiComponent
class ListPresenter(
    private val log: Logger,
    private val client: ApiClient)
    : IListPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IListView? = null

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IListView) {
        this.view = view
    }

    override fun unBind() {
        this.view = null
    }

}