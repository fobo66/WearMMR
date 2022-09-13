package io.github.fobo66.wearmmr.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.fobo66.domain.usecase.LoadPlayerId
import io.github.fobo66.domain.usecase.SavePlayerId
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val loadPlayerId: LoadPlayerId,
    private val savePlayerId: SavePlayerId
) : ViewModel() {
    suspend fun loadPlayerId(): String = loadPlayerId.execute()

    fun savePlayerId(newPlayerId: String) = viewModelScope.launch {
        savePlayerId.execute(newPlayerId)
    }
}
