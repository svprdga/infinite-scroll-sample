package com.svprdga.infinitescrollsample.presentation.presenter

import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.usecase.ShowsUseCase
import com.svprdga.infinitescrollsample.presentation.eventbus.FavoriteEvent
import com.svprdga.infinitescrollsample.presentation.eventbus.FavoritesBus
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IFavoritesPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IFavoritesView
import com.svprdga.infinitescrollsample.util.Logger
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

class FavoritesPresenter(
    private val log: Logger,
    private val showsUseCase: ShowsUseCase,
    private val favoritesBus: FavoritesBus
) : IFavoritesPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IFavoritesView? = null
    private var showsNumber = 0
    private var shows: List<Show>? = null

    private val observer = object : SingleObserver<List<Show>> {
        override fun onSubscribe(d: Disposable) {
            // Not needed
        }

        override fun onSuccess(fetchedShows: List<Show>) {
            shows = fetchedShows

            if (fetchedShows.isEmpty()) {
                view?.showEmptyFavoritesLayout()
            } else {
                showsNumber = fetchedShows.size
                view?.hideEmptyFavoritesLayout()
                view?.setFavorites(fetchedShows)
            }
        }

        override fun onError(e: Throwable) {
            e.message?.let { log.error(it) }
        }
    }

    private val favoriteDisposable = object : DisposableObserver<FavoriteEvent>() {
        override fun onNext(event: FavoriteEvent) {
            if (!event.show.isFavorite) {
                view?.removeShowFromList(event.layoutPosition)
                showsNumber--

                if (showsNumber < 1) {
                    view?.showEmptyFavoritesLayout()
                }
            }
        }

        override fun onError(e: Throwable) {
            e.message?.let { log.error(it) }
        }

        override fun onComplete() {
            // Do nothing.
        }
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IFavoritesView) {
        this.view = view
        favoritesBus.getFavoriteEvent().subscribe(favoriteDisposable)
    }

    override fun onStart() {
        // Load the favorites list.
        view?.clearList()
        showsUseCase.findAllFavoritesAsync()
            .subscribe(observer)
    }

    override fun unBind() {
        this.view = null
        favoriteDisposable.dispose()
    }

}