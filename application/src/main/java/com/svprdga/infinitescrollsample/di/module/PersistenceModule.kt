package com.svprdga.infinitescrollsample.di.module

import android.content.Context
import com.svprdga.infinitescrollsample.data.persistence.dao.ShowDao
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun providesRealm(context: Context): Realm {
        Realm.init(context)

        return Realm.getDefaultInstance()
    }

    @Provides
    @Singleton
    fun providesShowDao(realm: Realm): ShowDao {
        return ShowDao(realm)
    }
}