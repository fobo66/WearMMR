package io.github.fobo66.domain.usecase

import io.github.fobo66.domain.entities.RatingState

interface ResolveRatingState {
    suspend fun execute(): RatingState
}
