package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.domain.usecase.ShowsUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single {
        ShowsUseCase(get())
    }
}