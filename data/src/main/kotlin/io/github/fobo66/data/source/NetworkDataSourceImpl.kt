package io.github.fobo66.data.source

import io.github.fobo66.data.api.MatchmakingRatingApi
import io.github.fobo66.data.entities.PlayerInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NetworkDataSourceImpl(
    private val matchmakingRatingApi: MatchmakingRatingApi,
    private val ioDispatcher: CoroutineDispatcher
) : NetworkDataSource {
    override suspend fun fetchRating(playerId: Long): PlayerInfo = withContext(ioDispatcher) {
        matchmakingRatingApi.fetchPlayerProfile(playerId)
    }
}
