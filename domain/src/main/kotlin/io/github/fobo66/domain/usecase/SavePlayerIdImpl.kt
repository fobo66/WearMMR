package io.github.fobo66.domain.usecase

import io.github.fobo66.data.repositories.SettingsRepository

class SavePlayerIdImpl(
    private val settingsRepository: SettingsRepository
) : SavePlayerId {
    override suspend fun execute(newPlayerId: String) {
        if (newPlayerId.isNotBlank()) {
            settingsRepository.savePlayerId(newPlayerId.toLong())
        }
    }
}
