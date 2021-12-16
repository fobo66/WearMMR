package io.github.fobo66.wearmmr.domain.usecase

import io.github.fobo66.wearmmr.api.MatchmakingRatingApi
import io.github.fobo66.wearmmr.db.MatchmakingDatabase

class RatingComplicationUseCase(
    private val db: MatchmakingDatabase,
    private val matchmakingRatingClient: MatchmakingRatingApi
) {
}