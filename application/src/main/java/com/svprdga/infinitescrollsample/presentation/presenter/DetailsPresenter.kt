package com.svprdga.infinitescrollsample.presentation.presenter

import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IDetailsPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IDetailsView
import com.svprdga.infinitescrollsample.util.Logger

class DetailsPresenter(
    private val log: Logger
    ) : IDetailsPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IDetailsView? = null

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IDetailsView) {
        this.view = view
    }

    override fun unBind() {
        this.view = null
    }

}