package com.svprdga.infinitescrollsample.presentation.presenter

import com.svprdga.infinitescrollsample.domain.ShowData
import com.svprdga.infinitescrollsample.domain.repository.IShowRepository
import com.svprdga.infinitescrollsample.domain.usecase.ShowsUseCase
import com.svprdga.infinitescrollsample.presentation.eventbus.AppFragment
import com.svprdga.infinitescrollsample.presentation.eventbus.FragmentNavBus
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IListView
import com.svprdga.infinitescrollsample.util.Logger
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

class ListPresenter(
    private val log: Logger,
    private val showsUseCase: ShowsUseCase,
    private val navBus: FragmentNavBus
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

    private val navDisposable = object : DisposableObserver<AppFragment>() {
        override fun onNext(fragment: AppFragment) {
            if (fragment == AppFragment.LIST){
                view?.showAll()
            } else {
                view?.hideAll()
            }
        }

        override fun onError(e: Throwable) {
            // Do nothing.
        }

        override fun onComplete() {
            // Do nothing.
        }
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IListView) {
        this.view = view
        navBus.getNewSearch().subscribe(navDisposable)

        // Fetch first items.
        showsUseCase.findPopularShows(currentPage)
            .subscribe(popularShowsObserver)
    }

    override fun unBind() {
        this.view = null
        navDisposable.dispose()
    }

    override fun loadNextShowSet() {
        // Check that the next set exists
        if (!isLastPage) {
            showsUseCase.findPopularShows(++currentPage)
                .subscribe(popularShowsObserver)
        }
    }

}