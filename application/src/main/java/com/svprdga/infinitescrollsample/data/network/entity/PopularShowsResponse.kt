package com.svprdga.infinitescrollsample.data.network.entity

import com.google.gson.annotations.SerializedName
import com.svprdga.infinitescrollsample.domain.Mockable

@Mockable
class PopularShowsResponse {
    var page = 0
    @SerializedName("total_pages")
    var totalPages = 0
    var results: List<ShowEntity> = listOf()
}

class ShowEntity {
    var id = -1
    var name = ""
    var overview = ""
    @SerializedName("vote_average")
    var averageRating = 0.0f
    @SerializedName("poster_path")
    var imagePath: String? = null
}