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
import io.github.fobo66.data.fake.FakeNetworkDataSource
import io.github.fobo66.data.fake.FakePersistenceDataSource
import io.github.fobo66.data.fake.FakePreferenceDataSource
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class RatingRepositoryImplTest {

    private val persistenceDataSource = FakePersistenceDataSource()

    private val preferenceDataSource = FakePreferenceDataSource()

    private val networkDataSource = FakeNetworkDataSource()

    private val ratingRepository: RatingRepository =
        RatingRepositoryImpl(persistenceDataSource, networkDataSource)

    @Test
    fun `load rating from cache`() = runTest {
        preferenceDataSource.longNumber = 1L
        persistenceDataSource.rating = MatchmakingRating(
            1L,
            "test",
            "test",
            "test",
            1
        )

        ratingRepository.loadRating(1L)

        assertTrue {
            persistenceDataSource.isLoadedFromCache
        }
    }

    @Test
    fun `fetch rating always from network`() = runTest {
        preferenceDataSource.longNumber = 1L
        persistenceDataSource.rating = MatchmakingRating(
            1L,
            "test",
            "test",
            "test",
            1
        )

        ratingRepository.fetchRating(1L)

        assertFalse {
            persistenceDataSource.isLoadedFromCache
        }

        assertTrue {
            networkDataSource.isFetched
        }
    }

    @Test
    fun `no rating for given id - fetch rating`() = runTest {
        preferenceDataSource.longNumber = 1L

        ratingRepository.loadRating(1L)

        assertTrue {
            networkDataSource.isFetched
        }
    }
}
