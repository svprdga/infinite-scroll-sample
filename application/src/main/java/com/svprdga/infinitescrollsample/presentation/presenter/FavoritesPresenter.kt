package com.svprdga.infinitescrollsample.presentation.presenter

import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.repository.IShowRepository
import com.svprdga.infinitescrollsample.presentation.eventbus.AppFragment
import com.svprdga.infinitescrollsample.presentation.eventbus.FragmentNavBus
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IFavoritesPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IFavoritesView
import com.svprdga.infinitescrollsample.util.Logger
import com.svprdga.infinitescrollsample.util.TextProvider
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

class FavoritesPresenter(
    private val log: Logger,
    private val showRepository: IShowRepository,
    private val textProvider: TextProvider,
    private val navBus: FragmentNavBus
) : IFavoritesPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IFavoritesView? = null
    private var showsNumber = 0

    private val observer = object : SingleObserver<List<Show>> {
        override fun onSubscribe(d: Disposable) {
            // Not needed
        }

        override fun onSuccess(shows: List<Show>) {
            if (shows.isEmpty()) {
                view?.showEmptyFavoritesLayout()
            } else {
                showsNumber = shows.size
                view?.hideEmptyFavoritesLayout()
                view?.setFavorites(shows)
            }
        }

        override fun onError(e: Throwable) {
            e.message?.let { log.error(it) }
        }
    }

    private val navDisposable = object : DisposableObserver<AppFragment>() {
        override fun onNext(fragment: AppFragment) {
            if (fragment == AppFragment.LIST){
                view?.hideAll()
            } else {
                view?.showAll()
                showRepository.findAllFavorites()
                    .subscribe(observer)
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

    override fun bind(view: IFavoritesView) {
        this.view = view
        navBus.getNewSearch().subscribe(navDisposable)

        // Load the favorites list.
        showRepository.findAllFavorites()
            .subscribe(observer)
    }

    override fun unBind() {
        this.view = null
        navDisposable.dispose()
    }

    override fun makeShowNotFavorite(show: Show) {
        showRepository.removeFavorite(show)
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    // Not needed
                }

                override fun onComplete() {
                    log.debug("Show ${show.name} is not longer a favorite.")
                    view?.showSmallPopup(textProvider.showRemovedFromFavorites)
                    view?.removeShowFromList()
                    showsNumber--

                    if (showsNumber < 1) {
                        view?.showEmptyFavoritesLayout()
                    }
                }

                override fun onError(e: Throwable) {
                    e.message?.let { log.error(it) }
                }
            })
    }


}