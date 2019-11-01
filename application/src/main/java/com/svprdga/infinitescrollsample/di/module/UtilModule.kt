package com.svprdga.infinitescrollsample.di.module

import android.content.Context
import com.svprdga.infinitescrollsample.util.Logger
import com.svprdga.infinitescrollsample.util.TextProvider
import org.koin.dsl.module

private const val LOG_TAG = "iss_application"

//@Module
//class UtilModule {
//
//    @Provides
//    @Singleton
//    fun provideLogger(): Logger {
//        return Logger(LOG_TAG)
//    }
//
//    @Provides
//    @Singleton
//    fun provideTextProvider(context: Context): TextProvider {
//        return TextProvider(context)
//    }
//
//}

val utilModule = module {

    single {
        Logger(LOG_TAG)
    }

    single {
        TextProvider(get())
    }
}