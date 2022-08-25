package io.github.fobo66.data.fake

import io.github.fobo66.data.entities.PlayerInfo
import io.github.fobo66.data.source.NetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.appendPathSegments
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class FakeNetworkDataSource : NetworkDataSource, Clearable {
    var isFetched = false

    private val response = """
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

    private val engine = MockEngine {
        val status = if (it.url.encodedPath.contains('1')) {
            HttpStatusCode.OK
        } else {
            HttpStatusCode.InternalServerError
        }

        respond(
            content = response,
            status = status,
            headers = headersOf(
                "Content-Type",
                "application/json"
            )
        )
    }

    private val api = HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                },
                contentType = ContentType.Any
            )
        }
    }

    override suspend fun fetchRating(playerId: Long): PlayerInfo {
        isFetched = true
        return api.get("https://localhost") {
            url {
                appendPathSegments(playerId.toString())
            }
        }.body()
    }

    override fun clear() {
        isFetched = false
    }
}
