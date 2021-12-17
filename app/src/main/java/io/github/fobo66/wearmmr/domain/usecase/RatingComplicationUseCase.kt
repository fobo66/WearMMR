package io.github.fobo66.wearmmr.domain.usecase

import io.github.fobo66.wearmmr.api.CoroutinesMatchmakingRatingApi
import io.github.fobo66.wearmmr.db.MatchmakingDatabase
import io.github.fobo66.wearmmr.entities.toMatchmakingRating

class RatingComplicationUseCase(
    private val db: MatchmakingDatabase,
    private val matchmakingRatingClient: CoroutinesMatchmakingRatingApi
) {
    suspend fun execute() {
        val playerInfo = matchmakingRatingClient.fetchPlayerProfile(1)
        val rating = playerInfo.toMatchmakingRating()
        db.coroutinesGameStatsDao().insertRating(rating)
    }
}