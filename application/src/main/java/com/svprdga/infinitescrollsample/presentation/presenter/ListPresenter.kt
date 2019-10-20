package com.svprdga.infinitescrollsample.presentation.presenter

import com.svprdga.infinitescrollsample.data.repository.ShowRepository
import com.svprdga.infinitescrollsample.di.annotations.PerUiComponent
import com.svprdga.infinitescrollsample.domain.ShowData
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IListView
import com.svprdga.infinitescrollsample.util.Logger
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import java.lang.UnsupportedOperationException

@PerUiComponent
class ListPresenter(
    private val log: Logger,
    private val showRepository: ShowRepository
) : IListPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IListView? = null
    private var currentPage = 1
    private var isLastPage = false

    private val popularShowsObserver = object : SingleObserver<ShowData> {
        override fun onSubscribe(d: Disposable) {
            // Nothing.
        }

        override fun onSuccess(content: ShowData) {
            isLastPage = content.isLastPage
            view?.appendShows(content.shows)
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
        showRepository.findPopularShows(currentPage)
            .subscribe(popularShowsObserver)
    }

    override fun unBind() {
        this.view = null
    }

    override fun loadNextShowSet() {
        // Check that the next set exists
        if (!isLastPage) {
            showRepository.findPopularShows(++currentPage)
                .subscribe(popularShowsObserver)
        }
    }

}