package com.svprdga.infinitescrollsample.util

import android.content.Context
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Mockable
import javax.inject.Singleton

@Singleton
@Mockable
class TextProvider(private val context: Context) {

    fun getShowFavorite(name: String): String {
        return context.resources.getString(R.string.list_showFavorite)
            .replace("[name]", name)
    }

    val showRemovedFromFavorites: String
        get() = context.resources.getString(R.string.favorites_removed)
}