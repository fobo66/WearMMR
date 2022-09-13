package io.github.fobo66.domain.usecase

import io.github.fobo66.data.repositories.SettingsRepository

class LoadPlayerIdImpl(
    private val settingsRepository: SettingsRepository
) : LoadPlayerId {
    override suspend fun execute(): String {
        val playerId = settingsRepository.loadPlayerId()

        return if (playerId > 0) {
            playerId.toString()
        } else {
            ""
        }
    }
}
