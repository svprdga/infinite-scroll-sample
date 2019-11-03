package com.svprdga.infinitescrollsample.domain.usecase

import com.svprdga.infinitescrollsample.domain.Mockable
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.ShowData
import com.svprdga.infinitescrollsample.domain.repository.IShowRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.Function

/**
 * Use case in charge of all the operations related to [Show].
 */
@Mockable
class ShowsUseCase(private val showRepository: IShowRepository) {

    // ****************************************** VARS ***************************************** //

    val applyFavoriteState =
        Function<ShowData, ShowData> { input ->

            // Get the list of saved favorites in order to mark the fetched ones
            // with the correct state.
            val favorites = showRepository.findAllFavorites()

            input.shows.forEach { show ->
                if (favorites.contains(show)) {
                    show.isFavorite = true
                }
            }

            input
        }

    // ************************************* PUBLIC METHODS ************************************ //

    fun findPopularShows(page: Int): Single<ShowData> {
        return showRepository.findPopularShows(page)
            .map(applyFavoriteState)
    }

    fun insertShow(show: Show): Completable {
        return showRepository.insertShow(show)
    }

    fun findAllFavoritesAsync(): Single<List<Show>> {
        return showRepository.findAllFavoritesAsync()
    }

    fun findAllFavorites(): List<Show> {
        return showRepository.findAllFavorites()
    }

    fun removeFavorite(show: Show): Completable {
        return showRepository.removeFavorite(show)
    }

}