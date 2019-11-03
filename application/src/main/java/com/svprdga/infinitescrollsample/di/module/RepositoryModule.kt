package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.data.repository.ShowRepository
import com.svprdga.infinitescrollsample.domain.repository.IShowRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<IShowRepository> {
        ShowRepository(get(), get(), get())
    }
}