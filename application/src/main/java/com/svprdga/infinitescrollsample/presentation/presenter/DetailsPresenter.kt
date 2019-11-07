package com.svprdga.infinitescrollsample.presentation.presenter

import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.usecase.ShowsUseCase
import com.svprdga.infinitescrollsample.presentation.eventbus.FavoriteEvent
import com.svprdga.infinitescrollsample.presentation.eventbus.FavoritesBus
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IDetailsPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IDetailsView
import com.svprdga.infinitescrollsample.util.Logger
import com.svprdga.infinitescrollsample.util.TextProvider
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable

class DetailsPresenter(
    private val log: Logger,
    private val showsUseCase: ShowsUseCase,
    private val textProvider: TextProvider,
    private val favoritesBus: FavoritesBus
    ) : IDetailsPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IDetailsView? = null

    override var show: Show? = null

    private val removeFavoriteListener = object : CompletableObserver {
        override fun onSubscribe(d: Disposable) {
            // Not needed.
        }

        override fun onComplete() {
            show?.isFavorite = false
            view?.setUncheckedFavoriteIcon()
//            favoritesBus.setFavoriteEvent(FavoriteEvent(show!!, position))
        }

        override fun onError(e: Throwable) {
            e.message?.let { log.error(it) }
//            view?.showSmallPopup(textProvider.unexpectedError)
        }
    }

    private val addFavoriteListener = object : CompletableObserver {
        override fun onSubscribe(d: Disposable) {
            // Not needed.
        }

        override fun onComplete() {
            show?.isFavorite = true
//            favoritesBus.setFavoriteEvent(FavoriteEvent(show!!, position))
            view?.setFavoriteIcon()
        }

        override fun onError(e: Throwable) {
            e.message?.let { log.error(it) }
//            view?.showSmallPopup(textProvider.unexpectedError)
        }
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IDetailsView) {
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