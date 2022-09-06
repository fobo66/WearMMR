package io.github.fobo66.domain.usecase

import io.github.fobo66.data.repositories.RatingRepository

class RatingComplicationUseCase(
    private val ratingRepository: RatingRepository
) {
    suspend fun execute(): Int? {
        val rating = ratingRepository.fetchRating()
        return rating?.rating
    }
}
