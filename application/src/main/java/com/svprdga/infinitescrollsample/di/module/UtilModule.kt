package com.svprdga.infinitescrollsample.di.module

import android.os.Handler
import com.svprdga.infinitescrollsample.util.AndroidTimer
import com.svprdga.infinitescrollsample.util.Logger
import com.svprdga.infinitescrollsample.util.TextProvider
import com.svprdga.infinitescrollsample.util.abstraction.ITimer
import org.koin.dsl.module

private const val LOG_TAG = "iss_application"

val utilModule = module {

    single {
        Logger(LOG_TAG)
    }

    single {
        TextProvider(get())
    }

    factory {
        Handler()
    }

    factory<ITimer> {
        AndroidTimer()
    }
}