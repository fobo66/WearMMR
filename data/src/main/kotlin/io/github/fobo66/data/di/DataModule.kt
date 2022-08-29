package io.github.fobo66.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.preference.PreferenceManager
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.create
import io.github.fobo66.data.API_BASE_URL
import io.github.fobo66.data.api.MatchmakingRatingApi
import io.github.fobo66.data.repositories.RatingRepository
import io.github.fobo66.data.repositories.RatingRepositoryImpl
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
        }
    }

    single {
        Ktorfit.Builder()
            .baseUrl(API_BASE_URL)
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
            get(),
            get()
        )
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "wearmmr",
    produceMigrations = { context ->
        listOf(
            SharedPreferencesMigration(produceSharedPreferences = {
                PreferenceManager.getDefaultSharedPreferences(context)
            })
        )
    }
)
