package io.github.fobo66.wearmmr.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.fobo66.wearmmr.API_BASE_URL
import okhttp3.OkHttpClient
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * (c) 2017 Andrey Mukamolow <fobo66@protonmail.com>
 * Created 12/18/17.
 */

val apiModule = applicationContext {
    bean { provideHttpClient() }
    bean { provideApiClient(get()) }
}

fun provideHttpClient(): OkHttpClient {
    return OkHttpClient()
}

fun provideApiClient(httpClient: OkHttpClient): MatchmakingRatingApi {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient)
        .build()

    return retrofit.create(MatchmakingRatingApi::class.java)
}