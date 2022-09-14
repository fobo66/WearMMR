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

import io.github.fobo66.domain.fake.FakeRatingRepository
import io.github.fobo66.domain.fake.FakeSettingsRepository
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RatingComplicationUseCaseImplTest {

    private val settingsRepository = FakeSettingsRepository()
    private val ratingRepository = FakeRatingRepository()

    private val ratingComplicationUseCase: RatingComplicationUseCase =
        RatingComplicationUseCaseImpl(settingsRepository, ratingRepository)

    @Test
    fun `load rating`() = runTest {
        settingsRepository.playerId = 1L
        assertEquals(FakeRatingRepository.RATING, ratingComplicationUseCase.execute())
    }

    @Test
    fun `no rating`() = runTest {
        ratingRepository.noRating = true
        assertNull(ratingComplicationUseCase.execute())
    }

    @Test
    fun `no rating value`() = runTest {
        ratingRepository.rating = null
        assertNull(ratingComplicationUseCase.execute())
    }
}
