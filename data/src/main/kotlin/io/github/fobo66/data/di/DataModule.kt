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
import io.github.fobo66.data.source.PersistenceDataSource
import io.github.fobo66.data.source.PersistenceDataSourceImpl
import io.github.fobo66.data.source.PreferenceDataSource
import io.github.fobo66.data.source.PreferenceDataSourceImpl
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single {
        androidContext().dataStore
    }

    single<PreferenceDataSource> {
        PreferenceDataSourceImpl(get())
    }

    single<PersistenceDataSource> {
        PersistenceDataSourceImpl(get())
    }

    single<RatingRepository> {
        RatingRepositoryImpl(get(), get())
    }

    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json()
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
