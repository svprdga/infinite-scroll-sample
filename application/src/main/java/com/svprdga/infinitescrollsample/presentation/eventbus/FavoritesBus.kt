package com.svprdga.infinitescrollsample.presentation.eventbus

import com.svprdga.infinitescrollsample.domain.Mockable
import com.svprdga.infinitescrollsample.domain.Show
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

data class FavoriteEvent(val show: Show, val layoutPosition: Int)

@Mockable
class FavoritesBus {

    // ****************************************** VARS ***************************************** //

    private val subject = PublishSubject.create<FavoriteEvent>()

    // ************************************* PUBLIC METHODS ************************************ //

    fun setFavoriteEvent(event: FavoriteEvent) {
        subject.onNext(event)
    }

    fun getFavoriteEvent(): Observable<FavoriteEvent> {
        return subject
    }
}