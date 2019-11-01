package com.svprdga.infinitescrollsample.data.repository

import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.data.network.entity.mapper.Mapper
import com.svprdga.infinitescrollsample.data.persistence.dao.ShowDao
import com.svprdga.infinitescrollsample.domain.Mockable
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.ShowData
import com.svprdga.infinitescrollsample.domain.repository.IShowRepository
import io.reactivex.Completable
import io.reactivex.Single

@Mockable
class ShowRepository(
    private val apiClient: ApiClient,
    private val mapper: Mapper,
    private val showDao: ShowDao) :
    IShowRepository {

    override fun findPopularShows(page: Int): Single<ShowData> {
        return apiClient.getPopularShows(page)
            .map { entity -> mapper.showResponseToShowData().apply(entity) }
    }

    override fun insertShow(show: Show): Completable {
        return showDao.insert(mapper.showToShowDbEntity(show))
    }

    override fun findAllFavorites(): Single<List<Show>> {
        return showDao.findAll()
            .map { entities -> mapper.showDbEntitiesToShows().apply(entities) }
    }

    override fun removeFavorite(show: Show): Completable {
        return showDao.delete(show)
    }
}