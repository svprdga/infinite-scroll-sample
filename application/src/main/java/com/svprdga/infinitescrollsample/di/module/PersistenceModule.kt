package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.data.persistence.dao.ShowDao
import io.realm.Realm
import org.koin.dsl.module

val persistenceModule = module {

    single {
        Realm.init(get())

        Realm.getDefaultInstance()
    }

    single {
        ShowDao(get())
    }
}