package com.svprdga.infinitescrollsample.domain

data class ShowData(val page: Int, val isLastPage: Boolean, val shows: List<Show>)

data class Show(val id: Int, val name: String, val overview: String, val averageRating: Float)
