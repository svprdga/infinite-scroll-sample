package com.svprdga.infinitescrollsample.uitest.stub

import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IShowPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IShowView

class TestShowPresenter : IShowPresenter {

    var view: IShowView? = null

    override var show: Show? = null

    override fun favoriteButtonClick(itemPosition: Int) {

    }

    override fun bind(view: IShowView) {
        this.view = view
    }

    override fun unBind() {
    }

}