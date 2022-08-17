package io.github.fobo66.data.repositories

import io.github.fobo66.data.entities.MatchmakingRating
import io.github.fobo66.data.source.PersistenceDataSource
import io.github.fobo66.data.source.PreferenceDataSource

interface RatingRepository {
    suspend fun loadRating(): MatchmakingRating?
}

class RatingRepositoryImpl(
    private val persistenceDataSource: PersistenceDataSource,
    private val preferenceDataSource: PreferenceDataSource
) : RatingRepository {
    override suspend fun loadRating(): MatchmakingRating? {
        val playerId = preferenceDataSource.getLong(KEY_PLAYER_ID, NO_PLAYER_ID)
        return if (playerId != NO_PLAYER_ID) {
            persistenceDataSource.loadRating(playerId)
        } else {
            null
        }
    }

    companion object {
        private const val NO_PLAYER_ID = -1L
        private const val KEY_PLAYER_ID = "playerId"
    }
}
