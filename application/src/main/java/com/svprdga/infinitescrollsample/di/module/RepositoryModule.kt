package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.data.network.entity.mapper.Mapper
import com.svprdga.infinitescrollsample.data.repository.ShowRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideShowRepository(apiClient: ApiClient, mapper: Mapper): ShowRepository {
        return ShowRepository(apiClient, mapper)
    }
}