package io.github.fobo66.wearmmr.domain.usecase

import android.content.SharedPreferences
import io.github.fobo66.wearmmr.api.CoroutinesMatchmakingRatingApi
import io.github.fobo66.wearmmr.db.MatchmakingDatabase
import io.github.fobo66.wearmmr.entities.toMatchmakingRating

class RatingComplicationUseCase(
    private val db: MatchmakingDatabase,
    private val preferences: SharedPreferences,
    private val matchmakingRatingClient: CoroutinesMatchmakingRatingApi
) {
    suspend fun execute(): Int? {
        val playerId = preferences.getLong(KEY_PLAYER_ID, NO_PLAYER_ID)

        if (playerId == NO_PLAYER_ID) {
            return null
        }

        val playerInfo = matchmakingRatingClient.fetchPlayerProfile(playerId)
        val rating = playerInfo.toMatchmakingRating()
        db.coroutinesGameStatsDao().insertRating(rating)
        return rating.rating
    }

    companion object {
        private const val KEY_PLAYER_ID = "playerId"
        private const val NO_PLAYER_ID = -1L
    }
}
