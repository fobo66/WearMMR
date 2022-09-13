package io.github.fobo66.wearmmr.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.fobo66.domain.entities.RatingState
import io.github.fobo66.domain.usecase.ResolveRatingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val resolveRatingState: ResolveRatingState
) : ViewModel() {

    private val _state: MutableStateFlow<MainViewState> = MutableStateFlow(MainViewState.Loading)
    val state: StateFlow<MainViewState>
        get() = _state

    fun checkViewState() = viewModelScope.launch {
        val viewState = when (val state = resolveRatingState.execute()) {
            is RatingState.LoadedRating -> MainViewState.LoadedRating(
                state.playerName,
                state.personaName,
                state.rating,
                state.avatarUrl
            )
            RatingState.NoPlayerId -> MainViewState.FirstLaunch
            RatingState.NoRating -> MainViewState.NoRating
        }

        _state.emit(viewState)
    }
}
