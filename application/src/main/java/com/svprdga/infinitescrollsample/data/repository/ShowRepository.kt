package com.svprdga.infinitescrollsample.data.repository

import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.data.network.entity.mapper.Mapper
import com.svprdga.infinitescrollsample.domain.Mockable
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.ShowData
import io.reactivex.Single

@Mockable
class ShowRepository(
    private val apiClient: ApiClient,
    private val mapper: Mapper) {

    fun findPopularShows(page: Int): Single<ShowData> {
        return apiClient.getPopularShows(page)
            .map { entity -> mapper.showResponseToShowData().apply(entity) }
    }
}