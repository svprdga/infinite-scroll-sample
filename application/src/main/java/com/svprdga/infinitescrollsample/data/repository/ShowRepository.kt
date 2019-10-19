package com.svprdga.infinitescrollsample.data.repository

import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.data.network.entity.mapper.Mapper
import com.svprdga.infinitescrollsample.domain.Mockable
import com.svprdga.infinitescrollsample.domain.Show
import io.reactivex.Single

@Mockable
class ShowRepository(
    private val apiClient: ApiClient,
    private val mapper: Mapper) {

    fun findPopularShows(page: Int): Single<List<Show>> {
        return apiClient.getPopularShows(page)
            .map { entity -> mapper.showEntitiesToShows().apply(entity.results) }
    }
}