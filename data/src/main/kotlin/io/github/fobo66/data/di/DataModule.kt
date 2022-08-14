package io.github.fobo66.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.preference.PreferenceManager
import io.github.fobo66.data.source.PersistenceDataSource
import io.github.fobo66.data.source.PersistenceDataSourceImpl
import io.github.fobo66.data.source.PreferenceDataSource
import io.github.fobo66.data.source.PreferenceDataSourceImpl
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
