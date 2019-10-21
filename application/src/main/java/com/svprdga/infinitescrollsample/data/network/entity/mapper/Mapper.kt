package com.svprdga.infinitescrollsample.data.network.entity.mapper

import com.svprdga.infinitescrollsample.data.network.entity.PopularShowsResponse
import com.svprdga.infinitescrollsample.domain.*
import io.reactivex.functions.Function

@Mockable
class Mapper {

    fun showResponseToShowData(): Function<PopularShowsResponse, ShowData> {
        return Function {
            val list: ArrayList<Show> = arrayListOf()

            for (entity in it.results) {

                val imagePath = if (entity.imagePath != null) {
                    "$IMAGE_URL$W300${entity.imagePath!!.substring(1)}"
                } else {
                    null
                }

                list.add(
                    Show(
                        entity.id,
                        entity.name,
                        entity.overview,
                        entity.averageRating,
                        imagePath
                    )
                )
            }

            ShowData(it.page, it.page >= it.totalPages, list)
        }
    }

}