package com.svprdga.infinitescrollsample.presentation.presenter

import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.data.network.entity.PopularShowsResponse
import com.svprdga.infinitescrollsample.di.annotations.PerUiComponent
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IListView
import com.svprdga.infinitescrollsample.util.Logger
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@PerUiComponent
class ListPresenter(
    private val log: Logger,
    private val client: ApiClient
) : IListPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IListView? = null

    private val popularShowsObserver = object : SingleObserver<PopularShowsResponse> {
        override fun onSubscribe(d: Disposable) {
            // Nothing.
        }

        override fun onSuccess(content: PopularShowsResponse) {
        }

        override fun onError(e: Throwable) {
            e.message?.let { log.error(it) }
        }
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IListView) {
        this.view = view

        // TODO
        client.getPopularShows()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(popularShowsObserver)
    }

    override fun unBind() {
        this.view = null
    }

}