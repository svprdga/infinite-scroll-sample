package com.svprdga.infinitescrollsample.data.network.entity

import com.google.gson.annotations.SerializedName
import com.svprdga.infinitescrollsample.domain.Mockable

@Mockable
data class PopularShowsResponse(
    val page: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    val results: List<ShowEntity> = listOf()
)

data class ShowEntity(
    val id: Int = 0,
    val name: String = "",
    val overview: String = "",
    @SerializedName("vote_average")
    val averageRating: Float = 0.0f,
    @SerializedName("poster_path")
    val imagePath: String? = null
)