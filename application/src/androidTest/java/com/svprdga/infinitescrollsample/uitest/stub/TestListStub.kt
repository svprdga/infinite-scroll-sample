package com.svprdga.infinitescrollsample.uitest.stub

import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IListView

class TestListPresenter : IListPresenter {

    var view: IListView? = null
    var bindCalled = 0
    var onStartCalled = 0
    var loadNextShowCalled = 0
    var unBindCalled = 0

    override fun bind(view: IListView) {
        this.view = view
        bindCalled++
    }

    override fun onStart() {
        onStartCalled++
    }

    override fun loadNextShowSet() {
        loadNextShowCalled++
    }

    override fun unBind() {
        unBindCalled++
    }

    fun reset() {
        view = null
        bindCalled = 0
        onStartCalled = 0
        loadNextShowCalled = 0
        unBindCalled = 0
    }

}