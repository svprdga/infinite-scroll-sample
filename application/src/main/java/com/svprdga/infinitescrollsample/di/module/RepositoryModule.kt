package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.data.network.entity.mapper.Mapper
import com.svprdga.infinitescrollsample.data.persistence.dao.ShowDao
import com.svprdga.infinitescrollsample.data.repository.ShowRepository
import com.svprdga.infinitescrollsample.domain.repository.IShowRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideShowRepository(apiClient: ApiClient, mapper: Mapper,
                              showDao: ShowDao): IShowRepository {
        return ShowRepository(apiClient, mapper, showDao)
    }
}