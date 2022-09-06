package io.github.fobo66.domain.usecase

import io.github.fobo66.data.repositories.RatingRepository

interface RatingComplicationUseCase {
    suspend fun execute(): Int?
}

class RatingComplicationUseCaseImpl(
    private val ratingRepository: RatingRepository
) : RatingComplicationUseCase {
    override suspend fun execute(): Int? {
        val rating = ratingRepository.fetchRating()
        return rating?.rating
    }
}
