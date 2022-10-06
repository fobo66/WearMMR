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

package io.github.fobo66.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first

interface PreferenceDataSource {
    suspend fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    suspend fun saveBoolean(key: String, value: Boolean)
    suspend fun getLong(key: String, defaultValue: Long = 0L): Long
    suspend fun saveLong(key: String, value: Long)
}

class PreferenceDataSourceImpl(
    private val datastore: DataStore<Preferences>
) : PreferenceDataSource {
    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return datastore.data.first()[booleanPreferencesKey(key)] ?: defaultValue
    }

    override suspend fun saveBoolean(key: String, value: Boolean) {
        datastore.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    override suspend fun getLong(key: String, defaultValue: Long): Long {
        return datastore.data.first()[longPreferencesKey(key)] ?: defaultValue
    }

    override suspend fun saveLong(key: String, value: Long) {
        datastore.edit {
            it[longPreferencesKey(key)] = value
        }
    }
}
