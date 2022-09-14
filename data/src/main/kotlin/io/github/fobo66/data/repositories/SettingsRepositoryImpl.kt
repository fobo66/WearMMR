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

package io.github.fobo66.data.repositories

import io.github.fobo66.data.source.PreferenceDataSource

class SettingsRepositoryImpl(
    private val preferenceDataSource: PreferenceDataSource
) : SettingsRepository {
    override suspend fun loadFirstLaunch(): Boolean =
        preferenceDataSource.getBoolean(KEY_FIRST_LAUNCH, true)

    override suspend fun saveFirstLaunch(isFirstLaunch: Boolean) {
        preferenceDataSource.saveBoolean(KEY_FIRST_LAUNCH, isFirstLaunch)
    }

    override suspend fun loadPlayerId(): Long =
        preferenceDataSource.getLong(KEY_PLAYER_ID, NO_PLAYER_ID)

    override suspend fun savePlayerId(newPlayerId: Long) {
        preferenceDataSource.saveLong(KEY_PLAYER_ID, newPlayerId)
    }

    companion object {
        internal const val KEY_PLAYER_ID = "playerId"
        internal const val KEY_FIRST_LAUNCH = "firstLaunch"
        internal const val NO_PLAYER_ID = -1L
    }
}
