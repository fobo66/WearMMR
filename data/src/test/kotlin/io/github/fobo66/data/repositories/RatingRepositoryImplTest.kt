package io.github.fobo66.data.repositories

import io.github.fobo66.data.entities.MatchmakingRating
import io.github.fobo66.data.source.PersistenceDataSource
import io.github.fobo66.data.source.PreferenceDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNull

class RatingRepositoryImplTest {

    private val persistenceDataSource = object : PersistenceDataSource {
        override suspend fun loadRating(id: Long): MatchmakingRating? {
            return null
        }

        override suspend fun saveRating(rating: MatchmakingRating): Long {
            return 0L
        }
    }

    private val preferenceDataSource = object : PreferenceDataSource {
        override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean {
            return defaultValue
        }

        override suspend fun saveBoolean(key: String, value: Boolean) = Unit

        override suspend fun getLong(key: String, defaultValue: Long): Long {
            return defaultValue
        }

        override suspend fun saveLong(key: String, value: Long) = Unit
    }

    private lateinit var ratingRepository: RatingRepository

    @BeforeTest
    fun setUp() {
        ratingRepository = RatingRepositoryImpl(persistenceDataSource, preferenceDataSource)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun loadRating() = runTest {
        assertNull(ratingRepository.loadRating())
    }
}
