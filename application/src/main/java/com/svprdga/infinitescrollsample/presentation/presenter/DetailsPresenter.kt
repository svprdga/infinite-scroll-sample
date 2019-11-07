package com.svprdga.infinitescrollsample.presentation.presenter

import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.usecase.ShowsUseCase
import com.svprdga.infinitescrollsample.presentation.eventbus.FavoriteEvent
import com.svprdga.infinitescrollsample.presentation.eventbus.FavoritesBus
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IDetailsPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IDetailsView
import com.svprdga.infinitescrollsample.util.Logger
import com.svprdga.infinitescrollsample.util.TextProvider
import com.svprdga.infinitescrollsample.util.abstraction.ITimer
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable

private const val INIT_DELAY = 500L

class DetailsPresenter(
    private val log: Logger,
    private val showsUseCase: ShowsUseCase,
    private val textProvider: TextProvider,
    private val timer: ITimer
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
            show?.isFavorite = true
            view?.setFavoriteIcon()
        }

        override fun onError(e: Throwable) {
            e.message?.let { log.error(it) }
            view?.showSmallPopup(textProvider.unexpectedError)
        }
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IDetailsView) {
        this.view = view

        // Wait a little bit to start animations in order to give the user time to appreciate them.
        timer.executeDelayed(object: ITimer.TimerCallback {
            override fun onExecute() {
                view.startAnimations()
            }
        }, INIT_DELAY)
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