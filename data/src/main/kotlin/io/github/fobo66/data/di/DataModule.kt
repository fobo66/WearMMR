package io.github.fobo66.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
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
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "wearmmr")
