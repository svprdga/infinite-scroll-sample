package com.svprdga.infinitescrollsample.util

import android.content.Context
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Mockable

@Mockable
class TextProvider(private val context: Context) {

    val unexpectedError: String
        get() = context.resources.getString(R.string.unexpectedError)
}