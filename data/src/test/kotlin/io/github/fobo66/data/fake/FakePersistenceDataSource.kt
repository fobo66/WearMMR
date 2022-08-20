package io.github.fobo66.data.fake

import io.github.fobo66.data.entities.MatchmakingRating
import io.github.fobo66.data.source.PersistenceDataSource

class FakePersistenceDataSource : PersistenceDataSource, Clearable {

    var rating: MatchmakingRating? = null
    override suspend fun loadRating(id: Long): MatchmakingRating? {
        return rating
    }

    override suspend fun saveRating(rating: MatchmakingRating): Long {
        this.rating = rating
        return rating.playerId
    }

    override fun clear() {
        rating = null
    }
}
