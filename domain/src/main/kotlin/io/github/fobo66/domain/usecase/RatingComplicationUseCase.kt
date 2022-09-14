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

package io.github.fobo66.domain.usecase

import io.github.fobo66.data.repositories.RatingRepository
import io.github.fobo66.data.repositories.SettingsRepository

interface RatingComplicationUseCase {
    suspend fun execute(): Int?
}

class RatingComplicationUseCaseImpl(
    private val settingsRepository: SettingsRepository,
    private val ratingRepository: RatingRepository
) : RatingComplicationUseCase {
    override suspend fun execute(): Int? {
        val playerId = settingsRepository.loadPlayerId()
        val rating = if (playerId > 0) {
            ratingRepository.fetchRating(playerId)
        } else {
            null
        }
        return rating?.rating
    }
}
