package io.github.fobo66.wearmmr.model

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.fobo66.wearmmr.db.MatchmakingDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val preferences: SharedPreferences,
    private val matchmakingDatabase: MatchmakingDatabase
) : ViewModel() {

    private val _state: MutableStateFlow<MainViewState> = MutableStateFlow(MainViewState.Loading)
    val state: StateFlow<MainViewState>
        get() = _state

    fun checkViewState() = viewModelScope.launch {
        val isFirstLaunch = preferences.getBoolean(KEY_FIRST_LAUNCH, true)

        if (isFirstLaunch) {
            Timber.d("First time launching the app")
            _state.emit(MainViewState.FirstLaunch)
            preferences.edit {
                putBoolean(KEY_FIRST_LAUNCH, false)
            }
        } else {
            val playerId = preferences.getLong(KEY_PLAYER_ID, NO_PLAYER_ID)

            if (playerId != NO_PLAYER_ID) {
                val rating =
                    matchmakingDatabase.gameStatsDao().findOneByPlayerId(playerId)
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

    companion object {
        private const val NO_PLAYER_ID = -1L
        private const val KEY_PLAYER_ID = "playerId"
        private const val KEY_FIRST_LAUNCH = "firstLaunch"
    }

}
