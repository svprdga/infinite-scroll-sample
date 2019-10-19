package com.svprdga.infinitescrollsample.data.network.entity

import com.google.gson.annotations.SerializedName

class PopularShowsResponse {
    var page = 0
    @SerializedName("total_results")
    var totalResults = 0
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
}