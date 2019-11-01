package com.svprdga.infinitescrollsample.presentation.eventbus

import com.svprdga.infinitescrollsample.domain.Mockable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

enum class AppFragment {
    LIST,
    FAVORITES
}

@Mockable
class FragmentNavBus {

    // ****************************************** VARS ***************************************** //

    private val subject = PublishSubject.create<AppFragment>()

    // ************************************* PUBLIC METHODS ************************************ //

    fun setNewFragment(search: AppFragment) {
        subject.onNext(search)
    }

    fun getNewSearch(): Observable<AppFragment> {
        return subject
    }
}