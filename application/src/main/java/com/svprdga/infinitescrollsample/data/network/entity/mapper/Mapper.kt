package com.svprdga.infinitescrollsample.data.network.entity.mapper

import com.svprdga.infinitescrollsample.data.network.entity.PopularShowsResponse
import com.svprdga.infinitescrollsample.domain.Mockable
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.ShowData
import io.reactivex.functions.Function

@Mockable
class Mapper {

    fun showResponseToShowData(): Function<PopularShowsResponse, ShowData> {
        return Function {
            val list: ArrayList<Show> = arrayListOf()

            for (entity in it.results) {
                list.add(Show(entity.id, entity.name, entity.overview, entity.averageRating))
            }

            ShowData(it.page, it.page >= it.totalPages, list)
        }
    }

}