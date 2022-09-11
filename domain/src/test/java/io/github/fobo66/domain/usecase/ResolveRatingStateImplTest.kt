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
