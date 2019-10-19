package com.svprdga.infinitescrollsample.presentation.presenter

import com.svprdga.infinitescrollsample.data.repository.ShowRepository
import com.svprdga.infinitescrollsample.di.annotations.PerUiComponent
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IListView
import com.svprdga.infinitescrollsample.util.Logger
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

@PerUiComponent
class ListPresenter(
    private val log: Logger,
    private val showRepository: ShowRepository
) : IListPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IListView? = null

    private val popularShowsObserver = object : SingleObserver<List<Show>> {
        override fun onSubscribe(d: Disposable) {
            // Nothing.
        }

        override fun onSuccess(content: List<Show>) {

        }

        override fun onError(e: Throwable) {
            e.message?.let { log.error(it) }
            view?.hideListLayout()
            view?.showErrorLayout()
        }
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IListView) {
        this.view = view

        // Fetch first items.
        showRepository.findPopularShows(1)
            .subscribe(popularShowsObserver)
    }

    override fun unBind() {
        this.view = null
    }

}