package com.svprdga.infinitescrollsample.domain.repository

import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.ShowData
import io.reactivex.Completable
import io.reactivex.Single

interface IShowRepository {

    /**
     * Retrieve a list of popular shows.
     *
     * @param page Specify the pagination to fetch.
     *
     * @return A [Single] of [ShowData].
     */
    fun findPopularShows(page: Int): Single<ShowData>

    /**
     * Save the given [show] as a favorite.
     *
     * @return [Completable] which will complete when persisted.
     */
    fun insertShow(show: Show): Completable

    /**
     * Retrieve the list of favorite [Show].
     *
     * Use it for async execution.
     *
     * @return a [Single] with a [List] of [Show].
     */
    fun findAllFavoritesAsync(): Single<List<Show>>

    /**
     * Retrieve the list of favorite [Show].
     *
     * Use it for non-async execution.
     *
     * @return a [List] of [Show].
     */
    fun findAllFavorites(): List<Show>

    /**
     * Remove the given [Show] from favorites.
     *
     * @return [Completable] which will complete when removed.
     */
    fun removeFavorite(show: Show): Completable
}