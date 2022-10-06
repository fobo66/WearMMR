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

import io.github.fobo66.domain.entities.RatingState
import io.github.fobo66.domain.fake.FakeRatingRepository
import io.github.fobo66.domain.fake.FakeSettingsRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class ResolveRatingStateImplTest {
    private val ratingRepository = FakeRatingRepository()
    private val settingsRepository = FakeSettingsRepository()
    private val resolveRatingState: ResolveRatingState =
        ResolveRatingStateImpl(settingsRepository, ratingRepository)

    @Test
    fun `no player id`() = runTest {
        val state = resolveRatingState.execute()
        assertEquals(RatingState.NoPlayerId, state)
    }

    @Test
    fun `no player id after launch`() = runTest {
        settingsRepository.firstLaunch = false
        val state = resolveRatingState.execute()
        assertEquals(RatingState.NoPlayerId, state)
    }

    @Test
    fun `no rating`() = runTest {
        settingsRepository.firstLaunch = false
        settingsRepository.playerId = 1L
        ratingRepository.noRating = true
        val state = resolveRatingState.execute()
        assertEquals(RatingState.NoRating, state)
    }

    @Test
    fun `loaded rating`() = runTest {
        settingsRepository.firstLaunch = false
        settingsRepository.playerId = 1L
        val state = resolveRatingState.execute()
        assertTrue {
            state is RatingState.LoadedRating
        }
    }
}
