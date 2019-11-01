package com.svprdga.infinitescrollsample.presentation.presenter.abstraction

import com.svprdga.infinitescrollsample.presentation.eventbus.AppFragment
import com.svprdga.infinitescrollsample.presentation.presenter.view.IMainView

interface IMainPresenter : IPresenter<IMainView> {

    /**
     * The user is navigating through the app fragments.
     */
    fun onChangeFragment(fragment: AppFragment)

}