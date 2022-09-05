package io.github.fobo66.data.source

import io.github.fobo66.data.entities.PlayerInfo

interface NetworkDataSource {
    suspend fun fetchRating(playerId: Long): PlayerInfo
}
