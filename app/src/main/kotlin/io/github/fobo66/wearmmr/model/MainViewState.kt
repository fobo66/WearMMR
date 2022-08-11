package io.github.fobo66.wearmmr.model

import io.github.fobo66.data.entities.MatchmakingRating

sealed class MainViewState {
    object FirstLaunch : MainViewState()
    object Loading : MainViewState()
    data class LoadedRating(val rating: MatchmakingRating) : MainViewState()
    object NoRating : MainViewState()
}
