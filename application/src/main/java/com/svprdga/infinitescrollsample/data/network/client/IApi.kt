package com.svprdga.infinitescrollsample.data.network.client

import com.svprdga.infinitescrollsample.data.network.entity.PopularShowsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IApi {

    @GET("tv/popular")
    fun getPopularShows(
        @Query("api_key") apiKey: String, @Query("language") language: String, @Query(
            "page"
        ) page: Int
    ): Single<PopularShowsResponse>

}
