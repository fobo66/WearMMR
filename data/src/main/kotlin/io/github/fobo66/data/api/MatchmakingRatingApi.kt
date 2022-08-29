package io.github.fobo66.data.api

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import io.github.fobo66.data.entities.PlayerInfo

interface MatchmakingRatingApi {
    @GET("players/{id}")
    suspend fun fetchPlayerProfile(@Path("id") id: Long): PlayerInfo
}
