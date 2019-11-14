package com.svprdga.infinitescrollsample.uitest.stub

import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IFavoritesPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IFavoritesView

class TestFavoritesPresenter : IFavoritesPresenter {

    var view: IFavoritesView? = null
    var onStartCalled = 0
    var bindCalled = 0
    var unBindCalled = 0

    override fun onStart() {
        onStartCalled++
    }

    override fun bind(view: IFavoritesView) {
        this.view = view
        bindCalled++
    }

    override fun unBind() {
        unBindCalled++
    }

    fun reset() {
        view = null
        onStartCalled = 0
        bindCalled = 0
        unBindCalled = 0
    }

}