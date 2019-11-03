package com.svprdga.infinitescrollsample.util

import android.content.Context
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Mockable

@Mockable
class TextProvider(private val context: Context) {

    val unexpectedError: String
        get() = context.resources.getString(R.string.unexpectedError)

    fun getShowFavorite(name: String): String {
        return context.resources.getString(R.string.list_showFavorite)
            .replace("[name]", name)
    }

    val showRemovedFromFavorites: String
        get() = context.resources.getString(R.string.favorites_removed)

    val showRemovedFavorites: String
        get() = context.resources.getString(R.string.show_removedFavorites)

    val showAddedFavorites: String
        get() = context.resources.getString(R.string.show_addedFavorites)
}