package com.svprdga.infinitescrollsample.presentation.presenter

import com.svprdga.infinitescrollsample.presentation.eventbus.AppFragment
import com.svprdga.infinitescrollsample.presentation.eventbus.FragmentNavBus
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IMainPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IMainView

class MainPresenter(
    private val fragmentNavBus: FragmentNavBus
) : IMainPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IMainView? = null

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IMainView) {
        this.view = view
    }

    override fun unBind() {
        this.view = null
    }

    override fun onChangeFragment(fragment: AppFragment) {
        fragmentNavBus.setNewFragment(fragment)
    }
}