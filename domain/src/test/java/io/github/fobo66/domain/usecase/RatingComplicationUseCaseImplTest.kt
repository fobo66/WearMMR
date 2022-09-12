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
