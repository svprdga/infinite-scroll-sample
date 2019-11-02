package com.svprdga.infinitescrollsample.presentation.presenter

import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.repository.IShowRepository
import com.svprdga.infinitescrollsample.domain.usecase.ShowsUseCase
import com.svprdga.infinitescrollsample.presentation.eventbus.FavoriteEvent
import com.svprdga.infinitescrollsample.presentation.eventbus.FavoritesBus
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IShowPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IShowView
import com.svprdga.infinitescrollsample.util.Logger
import com.svprdga.infinitescrollsample.util.TextProvider
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable

class ShowPresenter(
    private val log: Logger,
    private val showsUseCase: ShowsUseCase,
    private val textProvider: TextProvider,
    private val favoritesBus: FavoritesBus
) : IShowPresenter {

    override var show: Show? = null

    // ****************************************** VARS ***************************************** //

    private var view: IShowView? = null

    private val removeFavoriteListener = object : CompletableObserver {
        override fun onSubscribe(d: Disposable) {
            // Not needed.
        }

        override fun onComplete() {
            view?.showSmallPopup(textProvider.showRemovedFavorites)
            show?.isFavorite = false
            favoritesBus.setFavoriteEvent(FavoriteEvent(show!!))
            view?.setUncheckedFavoriteIcon()
        }

        override fun onError(e: Throwable) {
            e.message?.let { log.error(it) }
            view?.showSmallPopup(textProvider.unexpectedError)
        }
    }

    private val addFavoriteListener = object : CompletableObserver {
        override fun onSubscribe(d: Disposable) {
            // Not needed.
        }

        override fun onComplete() {
            view?.showSmallPopup(textProvider.showAddedFavorites)
            show?.isFavorite = true
            favoritesBus.setFavoriteEvent(FavoriteEvent(show!!))
            view?.setFavoriteIcon()
        }

        override fun onError(e: Throwable) {
            e.message?.let { log.error(it) }
            view?.showSmallPopup(textProvider.unexpectedError)
        }
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IShowView) {
        this.view = view
    }

    override fun unBind() {
        this.view = null
    }

    override fun favoriteButtonClick() {
        show?.let {
            if (it.isFavorite) {
                showsUseCase.removeFavorite(it)
                    .subscribe(removeFavoriteListener)
            } else {
                showsUseCase.insertShow(it)
                    .subscribe(addFavoriteListener)
            }
        }
    }

}