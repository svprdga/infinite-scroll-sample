package com.svprdga.infinitescrollsample.presentation.presenter

import com.svprdga.infinitescrollsample.di.annotations.PerUiComponent
import com.svprdga.infinitescrollsample.domain.repository.IShowRepository
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IShowPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IShowView

@PerUiComponent
class ShowPresenter(
    private val showRepository: IShowRepository
) : IShowPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IShowView? = null

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IShowView) {
        this.view = view
    }

    override fun unBind() {
        this.view = null
    }

}