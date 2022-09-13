package io.github.fobo66.domain.entities

sealed class RatingState {
    object NoPlayerId : RatingState()
    data class LoadedRating(
        val playerName: String,
        val personaName: String,
        val rating: String,
        val avatarUrl: String
    ) : RatingState()

    object NoRating : RatingState()
}
