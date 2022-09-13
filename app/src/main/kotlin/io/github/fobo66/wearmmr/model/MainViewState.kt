package io.github.fobo66.wearmmr.model

sealed class MainViewState {
    object FirstLaunch : MainViewState()
    object Loading : MainViewState()
    data class LoadedRating(
        val playerName: String,
        val personaName: String,
        val rating: String,
        val avatarUrl: String
    ) : MainViewState()

    object NoRating : MainViewState()
}
