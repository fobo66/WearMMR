package io.github.fobo66.wearmmr.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.fobo66.data.repositories.RatingRepository
import io.github.fobo66.data.repositories.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val settingsRepository: SettingsRepository,
    private val ratingRepository: RatingRepository
) : ViewModel() {

    private val _state: MutableStateFlow<MainViewState> = MutableStateFlow(MainViewState.Loading)
    val state: StateFlow<MainViewState>
        get() = _state

    fun checkViewState() = viewModelScope.launch {
        val isFirstLaunch = settingsRepository.loadFirstLaunch()

        if (isFirstLaunch) {
            Timber.d("First time launching the app")
            _state.emit(MainViewState.FirstLaunch)
            settingsRepository.saveFirstLaunch(false)
        } else {
            val playerId = settingsRepository.loadPlayerId()

            if (playerId > 0) {
                val rating = ratingRepository.loadRating()
                if (rating != null) {
                    Timber.d("Loaded rating")
                    _state.emit(MainViewState.LoadedRating(rating))
                } else {
                    _state.emit(MainViewState.NoRating)
                }
            } else {
                Timber.d("No rating yet")
                _state.emit(MainViewState.NoRating)
            }
        }
    }
}
