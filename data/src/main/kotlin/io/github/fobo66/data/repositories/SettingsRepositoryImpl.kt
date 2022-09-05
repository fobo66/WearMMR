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
