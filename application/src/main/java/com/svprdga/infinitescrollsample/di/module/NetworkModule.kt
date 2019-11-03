package com.svprdga.infinitescrollsample.di.module

import com.svprdga.infinitescrollsample.BuildConfig
import com.svprdga.infinitescrollsample.data.network.client.ApiClient
import com.svprdga.infinitescrollsample.data.network.client.IApi
import com.svprdga.infinitescrollsample.data.network.entity.mapper.Mapper
import com.svprdga.infinitescrollsample.domain.API_URL
import com.svprdga.infinitescrollsample.util.Logger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECTION_TIMEOUT = 10000L
private const val READ_TIMEOUT = 5000L
private const val WRITE_TIMEOUT = 5000L
private const val RETRIES = 3

val networkModule = module {

    single {
        ApiClient(get(), get(), get(), BuildConfig.IMDB_API_KEY, get())
    }

    single {
        Mapper()
    }

    single<Interceptor> {

        val log: Logger = get()

        object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()

                var response: Response? = null
                var responseOk = false
                var retryCount = 0

                while (!responseOk && retryCount < RETRIES) {
                    try {
                        response = chain.proceed(request)
                        responseOk = true
                    } catch (e: Exception) {
                        log.error("A request timed out:")
                        log.error("-- URI: ${request.url}")
                        log.error("-- Request: $request")
                    } finally {
                        retryCount++
                    }
                }

                return response ?: chain.proceed(request)
            }
        }
    }

    single {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        httpClientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
        httpClientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)

        val interceptor: Interceptor = get()
        httpClientBuilder.addInterceptor(interceptor)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(logging)
        }
        val builder = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        builder.client(httpClientBuilder.build()).build()
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(IApi::class.java)
    }

}