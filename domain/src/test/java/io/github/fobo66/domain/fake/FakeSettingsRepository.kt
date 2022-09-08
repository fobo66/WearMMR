package io.github.fobo66.domain.fake

import io.github.fobo66.data.repositories.SettingsRepository

class FakeSettingsRepository : SettingsRepository, Clearable {
    var firstLaunch = true
    var playerId = -1L

    override suspend fun loadFirstLaunch(): Boolean = firstLaunch

    override suspend fun saveFirstLaunch(isFirstLaunch: Boolean) {
        firstLaunch = isFirstLaunch
    }

    override suspend fun loadPlayerId(): Long = playerId

    override suspend fun savePlayerId(newPlayerId: Long) {
        playerId = newPlayerId
    }

    override fun clear() {
        firstLaunch = true
        playerId = -1L
    }
}
