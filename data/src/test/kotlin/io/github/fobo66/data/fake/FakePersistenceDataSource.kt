package io.github.fobo66.data.fake

import io.github.fobo66.data.entities.MatchmakingRating
import io.github.fobo66.data.source.PersistenceDataSource

class FakePersistenceDataSource : PersistenceDataSource {
    var isLoadedFromCache = false

    var rating: MatchmakingRating? = null
    override suspend fun loadRating(id: Long): MatchmakingRating? {
        isLoadedFromCache = true
        return rating
    }

    override suspend fun saveRating(rating: MatchmakingRating): Long {
        this.rating = rating
        return rating.playerId
    }
}
