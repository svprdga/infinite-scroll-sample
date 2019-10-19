package com.svprdga.infinitescrollsample.data.network.entity.mapper

import com.svprdga.infinitescrollsample.data.network.entity.ShowEntity
import com.svprdga.infinitescrollsample.domain.Mockable
import com.svprdga.infinitescrollsample.domain.Show
import io.reactivex.functions.Function

@Mockable
class Mapper {

    fun showEntitiesToShows(): Function<List<ShowEntity>, List<Show>> {
        return Function {
            val list: ArrayList<Show> = arrayListOf()

            for (entity in it) {
                list.add(Show(entity.id, entity.name, entity.overview, entity.averageRating))
            }
            list
        }
    }

}