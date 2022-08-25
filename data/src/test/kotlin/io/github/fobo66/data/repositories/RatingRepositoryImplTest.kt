package io.github.fobo66.data.repositories

import io.github.fobo66.data.entities.MatchmakingRating
import io.github.fobo66.data.fake.FakeNetworkDataSource
import io.github.fobo66.data.fake.FakePersistenceDataSource
import io.github.fobo66.data.fake.FakePreferenceDataSource
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

class RatingRepositoryImplTest {

    private val persistenceDataSource = FakePersistenceDataSource()

    private val preferenceDataSource = FakePreferenceDataSource()

    private val networkDataSource = FakeNetworkDataSource()

    private lateinit var ratingRepository: RatingRepository

    @BeforeTest
    fun setUp() {
        ratingRepository =
            RatingRepositoryImpl(persistenceDataSource, preferenceDataSource, networkDataSource)
    }

    @AfterTest
    fun tearDown() {
        networkDataSource.clear()
        persistenceDataSource.clear()
        preferenceDataSource.clear()
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `no rating by default`() = runTest {
        assertNull(ratingRepository.loadRating())
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `load rating from cache`() = runTest {
        preferenceDataSource.long = 1L
        persistenceDataSource.rating = MatchmakingRating(
            1L,
            "test",
            "test",
            "test",
            1
        )

        ratingRepository.loadRating()

        assertTrue {
            persistenceDataSource.isLoadedFromCache
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `no rating for given id - fetch rating`() = runTest {
        preferenceDataSource.long = 1L

        ratingRepository.loadRating()

        assertTrue {
            networkDataSource.isFetched
        }
    }
}
