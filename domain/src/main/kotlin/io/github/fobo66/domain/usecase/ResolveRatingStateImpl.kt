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
import io.github.fobo66.domain.entities.RatingState
import timber.log.Timber

class ResolveRatingStateImpl(
    private val settingsRepository: SettingsRepository,
    private val ratingRepository: RatingRepository
) : ResolveRatingState {
    override suspend fun execute(): RatingState {
        val isFirstLaunch = settingsRepository.loadFirstLaunch()

        return if (isFirstLaunch) {
            Timber.d("First time launching the app")
            settingsRepository.saveFirstLaunch(false)
            RatingState.NoPlayerId
        } else {
            val playerId = settingsRepository.loadPlayerId()

            if (playerId > 0) {
                val rating = ratingRepository.loadRating(playerId)
                if (rating != null) {
                    Timber.d("Loaded rating")
                    RatingState.LoadedRating(
                        playerName = rating.name.orEmpty(),
                        personaName = rating.personaName.orEmpty(),
                        rating = rating.rating.toString(),
                        avatarUrl = rating.avatarUrl.orEmpty()
                    )
                } else {
                    RatingState.NoRating
                }
            } else {
                Timber.d("No player id found")
                RatingState.NoPlayerId
            }
        }
    }
}
