package com.svprdga.infinitescrollsample.presentation.presenter.abstraction

import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.view.IShowView

interface IShowPresenter : IPresenter<IShowView> {

    var show:Show?

    /**
     * The user clicked on the favorite button to mark/unmkar the favorite state.
     */
    fun favoriteButtonClick()

}