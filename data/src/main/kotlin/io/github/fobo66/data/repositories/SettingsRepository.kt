package io.github.fobo66.data.repositories

interface SettingsRepository {
    suspend fun loadFirstLaunch(): Boolean
    suspend fun saveFirstLaunch(isFirstLaunch: Boolean)
    suspend fun loadPlayerId(): Long
    suspend fun savePlayerId(newPlayerId: Long)
}
