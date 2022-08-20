package io.github.fobo66.data.repositories

import io.github.fobo66.data.entities.MatchmakingRating
import io.github.fobo66.data.fake.FakePersistenceDataSource
import io.github.fobo66.data.fake.FakePreferenceDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class RatingRepositoryImplTest {

    private val persistenceDataSource = FakePersistenceDataSource()

    private val preferenceDataSource = FakePreferenceDataSource()

    private lateinit var ratingRepository: RatingRepository

    @BeforeTest
    fun setUp() {
        ratingRepository = RatingRepositoryImpl(persistenceDataSource, preferenceDataSource)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `no rating by default`() = runTest {
        assertNull(ratingRepository.loadRating())
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun loadRating() = runTest {
        preferenceDataSource.long = 1L
        persistenceDataSource.rating = MatchmakingRating(
            1L,
            "test",
            "test",
            "test",
            1
        )
        assertNotNull(ratingRepository.loadRating())
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `no rating for given id`() = runTest {
        preferenceDataSource.long = 1L
        assertNull(ratingRepository.loadRating())
    }
}
