/*
 *    Copyright 2022 Andrey Mukamolov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
