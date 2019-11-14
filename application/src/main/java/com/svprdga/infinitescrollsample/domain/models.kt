package com.svprdga.infinitescrollsample.domain

import java.io.Serializable

@Mockable
data class ShowData(val page: Int, val isLastPage: Boolean, val shows: List<Show>)

data class Show(
    val id: Int,
    val name: String,
    val overview: String,
    val averageRating: Float,
    val imagePath: String?,
    var isFavorite: Boolean
) : Serializable{

    override fun equals(other: Any?): Boolean {
        return if (other is Show) {
            id == other.id
        } else {
            super.equals(other)
        }

    }
}
