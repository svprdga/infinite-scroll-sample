package com.svprdga.infinitescrollsample.uitest.stub

import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IShowPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IShowView

class TestShowPresenter : IShowPresenter {

    var view: IShowView? = null
    var favoriteButtonClickCalled = 0
    var bindCalled = 0
    var unBindCalled = 0

    override var show: Show? = null

    override fun favoriteButtonClick(itemPosition: Int) {
        favoriteButtonClickCalled++
    }

    override fun bind(view: IShowView) {
        this.view = view
        bindCalled++
    }

    override fun unBind() {
        unBindCalled++
    }

    fun reset() {
        view = null
        favoriteButtonClickCalled = 0
        bindCalled = 0
        unBindCalled = 0
    }

}