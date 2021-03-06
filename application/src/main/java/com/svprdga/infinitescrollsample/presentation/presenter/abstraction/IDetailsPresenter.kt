package com.svprdga.infinitescrollsample.presentation.presenter.abstraction

import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.view.IDetailsView

interface IDetailsPresenter : IPresenter<IDetailsView> {

    var show: Show?

    /**
     * The user clicked on the favorite button to mark/unmark the favorite state.
     */
    fun favoriteButtonClick()

    /**
     * The user wants to buy the show.
     */
    fun onBuy()

    /**
     * The user wants to rent the show.
     */
    fun onRent()

}