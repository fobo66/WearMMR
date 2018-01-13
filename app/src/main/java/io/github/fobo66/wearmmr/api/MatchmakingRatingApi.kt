package io.github.fobo66.wearmmr.api

import io.github.fobo66.wearmmr.entities.PlayerInfo
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * (c) 2017 Andrey Mukamolow <fobo66@protonmail.com>
 * Created 12/17/17.
 */
interface MatchmakingRatingApi {

  @GET("players/{id}")
  fun fetchPlayerProfile(@Path("id") id: Int): Observable<PlayerInfo>
}