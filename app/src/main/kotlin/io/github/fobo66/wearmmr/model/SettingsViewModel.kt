package io.github.fobo66.wearmmr.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.fobo66.data.repositories.SettingsRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    suspend fun loadPlayerId(): String {
        val playerId = settingsRepository.loadPlayerId()

        return if (playerId > 0) {
            playerId.toString()
        } else {
            ""
        }
    }

    fun savePlayerId(newPlayerId: String) = viewModelScope.launch {
        settingsRepository.savePlayerId(newPlayerId.toLong())
    }
}
