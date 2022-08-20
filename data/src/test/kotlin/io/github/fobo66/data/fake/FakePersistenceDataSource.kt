package io.github.fobo66.data.fake

import io.github.fobo66.data.entities.MatchmakingRating
import io.github.fobo66.data.source.PersistenceDataSource

class FakePersistenceDataSource : PersistenceDataSource {
    override suspend fun loadRating(id: Long): MatchmakingRating? {
        return null
    }

    override suspend fun saveRating(rating: MatchmakingRating): Long {
        return 0L
    }
}
