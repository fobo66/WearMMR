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

package io.github.fobo66.data.repositories

import io.github.fobo66.data.entities.MatchmakingRating
import io.github.fobo66.data.entities.toMatchmakingRating
import io.github.fobo66.data.source.NetworkDataSource
import io.github.fobo66.data.source.PersistenceDataSource
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import timber.log.Timber

interface RatingRepository {
    suspend fun loadRating(playerId: Long): MatchmakingRating?
    suspend fun fetchRating(playerId: Long): MatchmakingRating?
}

class RatingRepositoryImpl(
    private val persistenceDataSource: PersistenceDataSource,
    private val networkDataSource: NetworkDataSource
) : RatingRepository {
    override suspend fun loadRating(playerId: Long): MatchmakingRating? {
        return persistenceDataSource.loadRating(playerId) ?: obtainRating(playerId)
    }

    override suspend fun fetchRating(playerId: Long): MatchmakingRating? {
        return obtainRating(playerId)
    }

    private suspend fun obtainRating(playerId: Long): MatchmakingRating? {
        return try {
            val networkRating = networkDataSource.fetchRating(playerId).toMatchmakingRating()
            persistenceDataSource.saveRating(networkRating)
            networkRating
        } catch (e: ClientRequestException) {
            Timber.e(e, "Failed to load rating due to bad request")
            null
        } catch (e: ServerResponseException) {
            Timber.e(e, "Failed to load rating due to server")
            null
        }
    }
}
