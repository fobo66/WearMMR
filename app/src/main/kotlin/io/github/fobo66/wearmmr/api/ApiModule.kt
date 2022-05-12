/*
 * Copyright 2018. Andrey Mukamolov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.fobo66.wearmmr.api

import io.github.fobo66.wearmmr.API_BASE_URL
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

/**
 * DI components for http client
 * Created 12/18/17.
 */

val apiModule = module {
    single { provideHttpClient() }
    single { provideApiClient(get()) }
}

fun provideHttpClient(): OkHttpClient {
    return OkHttpClient()
}

fun provideApiClient(httpClient: OkHttpClient): MatchmakingRatingApi {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(httpClient)
        .build()

    return retrofit.create()
}
