package io.github.fobo66.data.source

import io.github.fobo66.data.db.MatchmakingDao
import io.github.fobo66.data.entities.MatchmakingRating

interface PersistenceDataSource {
    suspend fun loadRating(id: Long): MatchmakingRating?
    suspend fun saveRating(rating: MatchmakingRating): Long
}

class PersistenceDataSourceImpl(
    private val dao: MatchmakingDao
) : PersistenceDataSource {
    override suspend fun loadRating(id: Long): MatchmakingRating? =
        dao.findOneByPlayerId(id)

    override suspend fun saveRating(rating: MatchmakingRating) =
        dao.insertRating(rating)
}
