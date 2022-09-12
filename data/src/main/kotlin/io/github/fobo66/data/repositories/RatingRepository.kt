package io.github.fobo66.data.repositories

import io.github.fobo66.data.entities.MatchmakingRating
import io.github.fobo66.data.entities.toMatchmakingRating
import io.github.fobo66.data.source.NetworkDataSource
import io.github.fobo66.data.source.PersistenceDataSource

interface RatingRepository {
    suspend fun loadRating(playerId: Long): MatchmakingRating?
    suspend fun fetchRating(playerId: Long): MatchmakingRating?
}

class RatingRepositoryImpl(
    private val persistenceDataSource: PersistenceDataSource,
    private val networkDataSource: NetworkDataSource
) : RatingRepository {
    override suspend fun loadRating(playerId: Long): MatchmakingRating {
        return persistenceDataSource.loadRating(playerId) ?: obtainRating(playerId)
    }

    override suspend fun fetchRating(playerId: Long): MatchmakingRating {
        return obtainRating(playerId)
    }

    private suspend fun obtainRating(playerId: Long): MatchmakingRating {
        val networkRating = networkDataSource.fetchRating(playerId).toMatchmakingRating()
        persistenceDataSource.saveRating(networkRating)
        return networkRating
    }
}
