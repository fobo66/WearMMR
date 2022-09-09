package io.github.fobo66.domain.fake

import io.github.fobo66.data.entities.MatchmakingRating
import io.github.fobo66.data.repositories.RatingRepository

class FakeRatingRepository : RatingRepository {
    var noRating = false
    var rating: Int? = RATING
    override suspend fun loadRating(): MatchmakingRating? =
        if (noRating) {
            null
        } else {
            MatchmakingRating(1L, "test", "test", "test", rating)
        }

    override suspend fun fetchRating(): MatchmakingRating? =
        if (noRating) {
            null
        } else {
            MatchmakingRating(2L, "test", "test", "test", rating)
        }

    companion object {
        const val RATING = 1000
    }
}
