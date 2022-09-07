package io.github.fobo66.domain.usecase

import io.github.fobo66.domain.fake.FakeRatingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class RatingComplicationUseCaseImplTest {

    private val ratingRepository = FakeRatingRepository()

    private lateinit var ratingComplicationUseCase: RatingComplicationUseCase

    @BeforeTest
    fun setUp() {
        ratingComplicationUseCase = RatingComplicationUseCaseImpl(ratingRepository)
    }

    @AfterTest
    fun tearDown() {
        ratingRepository.clear()
    }

    @Test
    fun `load rating`() = runTest {
        assertEquals(ratingComplicationUseCase.execute(), FakeRatingRepository.RATING)
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
