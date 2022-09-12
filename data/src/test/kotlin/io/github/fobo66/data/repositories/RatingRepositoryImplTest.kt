package io.github.fobo66.data.repositories

import io.github.fobo66.data.entities.MatchmakingRating
import io.github.fobo66.data.fake.FakeNetworkDataSource
import io.github.fobo66.data.fake.FakePersistenceDataSource
import io.github.fobo66.data.fake.FakePreferenceDataSource
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

class RatingRepositoryImplTest {

    private val persistenceDataSource = FakePersistenceDataSource()

    private val preferenceDataSource = FakePreferenceDataSource()

    private val networkDataSource = FakeNetworkDataSource()

    private val ratingRepository: RatingRepository =
        RatingRepositoryImpl(persistenceDataSource, networkDataSource)

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `no rating by default`() = runTest {
        assertNull(ratingRepository.loadRating(1L))
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
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
    @OptIn(ExperimentalCoroutinesApi::class)
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
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `no rating for given id - fetch rating`() = runTest {
        preferenceDataSource.longNumber = 1L

        ratingRepository.loadRating(1L)

        assertTrue {
            networkDataSource.isFetched
        }
    }
}
