/*
 *    Copyright 2022 Andrey Mukamolov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.fobo66.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import de.jensklingenberg.ktorfit.Ktorfit
import io.github.fobo66.data.ApiBaseUrl
import io.github.fobo66.data.api.MatchmakingRatingApi
import io.github.fobo66.data.repositories.RatingRepository
import io.github.fobo66.data.repositories.RatingRepositoryImpl
import io.github.fobo66.data.repositories.SettingsRepository
import io.github.fobo66.data.repositories.SettingsRepositoryImpl
import io.github.fobo66.data.source.NetworkDataSource
import io.github.fobo66.data.source.NetworkDataSourceImpl
import io.github.fobo66.data.source.PersistenceDataSource
import io.github.fobo66.data.source.PersistenceDataSourceImpl
import io.github.fobo66.data.source.PreferenceDataSource
import io.github.fobo66.data.source.PreferenceDataSourceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val dataModule = module {

    single {
        androidContext().dataStore
    }

    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            expectSuccess = true
        }
    }

    single {
        Ktorfit.Builder()
            .baseUrl(ApiBaseUrl)
            .httpClient(get<HttpClient>())
            .build()
            .create<MatchmakingRatingApi>()
    }

    single<PreferenceDataSource> {
        PreferenceDataSourceImpl(get())
    }

    single<PersistenceDataSource> {
        PersistenceDataSourceImpl(get())
    }

    single<NetworkDataSource> {
        NetworkDataSourceImpl(
            get(),
            get(qualifier("io"))
        )
    }

    single<RatingRepository> {
        RatingRepositoryImpl(
            get(),
            get()
        )
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "wearmmr"
)
