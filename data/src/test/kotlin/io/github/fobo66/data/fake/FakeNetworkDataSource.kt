package io.github.fobo66.data.fake

import io.github.fobo66.data.entities.PlayerInfo
import io.github.fobo66.data.source.NetworkDataSource
import kotlinx.serialization.json.Json

class FakeNetworkDataSource : NetworkDataSource {
    var isFetched = false

    private val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    override suspend fun fetchRating(playerId: Long): PlayerInfo {
        isFetched = true
        return json.decodeFromString(PlayerInfo.serializer(), response)
    }

    companion object {
        val response = """
        {
          "solo_competitive_rank": 0,
          "competitive_rank": 0,
          "rank_tier": 0,
          "leaderboard_rank": 0,
          "mmr_estimate": {
            "estimate": 0
          },
          "profile": {
            "account_id": 0,
            "personaname": "string",
            "name": "string",
            "plus": true,
            "cheese": 0,
            "steamid": "string",
            "avatar": "string",
            "avatarmedium": "string",
            "avatarfull": "string",
            "profileurl": "string",
            "last_login": "string",
            "loccountrycode": "string",
            "is_contributor": false,
            "is_subscriber": false
          }
        }
        """.trimIndent()
    }
}
