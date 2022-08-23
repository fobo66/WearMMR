/*
 * Copyright 2018. Andrey Mukamolov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.fobo66.wearmmr.api

import io.github.fobo66.data.entities.PlayerInfo
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * OpenDota API client
 */
interface MatchmakingRatingApi {

    @GET("players/{id}")
    suspend fun fetchPlayerProfile(@Path("id") id: Long): PlayerInfo
}
