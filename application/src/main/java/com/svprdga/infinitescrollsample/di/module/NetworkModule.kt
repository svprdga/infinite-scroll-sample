package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.BuildConfig
import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.data.network.client.IApi
import com.svprdga.infinitescrollsample.data.network.entity.mapper.Mapper
import com.svprdga.infinitescrollsample.data.network.rx.scheduler.ISchedulerProvider
import com.svprdga.infinitescrollsample.data.network.rx.scheduler.SchedulerProvider
import com.svprdga.infinitescrollsample.domain.API_URL
import com.svprdga.infinitescrollsample.util.Logger
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIMEOUT = 5000L

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideSchedulerProvider(): ISchedulerProvider {
        return SchedulerProvider()
    }

    @Provides
    @Singleton
    fun provideApiClient(
        log: Logger, retrofit: Retrofit,
        api: IApi, schedulerProvider: ISchedulerProvider
    ): ApiClient {
        return ApiClient(
            log,
            retrofit,
            api,
            BuildConfig.IMDB_API_KEY,
            schedulerProvider
        )
    }

    @Provides
    @Singleton
    fun provideMapper(): Mapper {
        return Mapper()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        httpClientBuilder.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        httpClientBuilder.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(logging)
        }
        val builder = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        return builder.client(httpClientBuilder.build()).build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): IApi {
        return retrofit.create(IApi::class.java)
    }

}