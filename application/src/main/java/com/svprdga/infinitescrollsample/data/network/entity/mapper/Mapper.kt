package com.svprdga.infinitescrollsample.data.network.entity.mapper

import com.svprdga.infinitescrollsample.data.network.entity.PopularShowsResponse
import com.svprdga.infinitescrollsample.data.persistence.entity.ShowDbEntity
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
                        imagePath,
                        false
                    )
                )
            }

            ShowData(it.page, it.page >= it.totalPages, list)
        }
    }

    /**
     * Map a [Show] to [ShowDbEntity].
     */
    fun showToShowDbEntity(show: Show): ShowDbEntity {
        return ShowDbEntity(
            show.id,
            show.name,
            show.overview,
            show.averageRating,
            show.imagePath
        )
    }

    /**
     * Map an [Array] of [ShowDbEntity] to a [List] of [Show].
     */
    fun showDbEntitiesToShows(): Function<Array<ShowDbEntity>, List<Show>> {
        return Function {
            val list = arrayListOf<Show>()

            it.forEach { entity ->
                val show = Show(
                    entity.id, entity.name, entity.overview, entity.averageRating, entity.imagePath,
                    true
                )
                list.add(show)
            }

            list
        }
    }

}