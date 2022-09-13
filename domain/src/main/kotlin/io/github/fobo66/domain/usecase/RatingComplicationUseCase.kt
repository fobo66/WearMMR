package io.github.fobo66.domain.usecase

import io.github.fobo66.data.repositories.RatingRepository
import io.github.fobo66.data.repositories.SettingsRepository

interface RatingComplicationUseCase {
    suspend fun execute(): Int?
}

class RatingComplicationUseCaseImpl(
    private val settingsRepository: SettingsRepository,
    private val ratingRepository: RatingRepository
) : RatingComplicationUseCase {
    override suspend fun execute(): Int? {
        val playerId = settingsRepository.loadPlayerId()
        val rating = if (playerId > 0) {
            ratingRepository.fetchRating(playerId)
        } else {
            null
        }
        return rating?.rating
    }
}
