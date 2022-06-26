package io.github.fobo66.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first

interface PreferenceDataSource {
    suspend fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    suspend fun getLong(key: String, defaultValue: Long = 0L): Long
}

class PreferenceDataSourceImpl(
    private val datastore: DataStore<Preferences>
) : PreferenceDataSource {
    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return datastore.data.first()[booleanPreferencesKey(key)] ?: defaultValue
    }

    override suspend fun getLong(key: String, defaultValue: Long): Long {
        return datastore.data.first()[longPreferencesKey(key)] ?: defaultValue
    }
}
