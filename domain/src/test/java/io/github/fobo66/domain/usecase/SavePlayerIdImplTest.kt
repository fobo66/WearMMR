package io.github.fobo66.domain.usecase

import io.github.fobo66.domain.fake.FakeSettingsRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class SavePlayerIdImplTest {

    private val settingsRepository = FakeSettingsRepository()

    private val savePlayerId: SavePlayerId = SavePlayerIdImpl(settingsRepository)

    @Test
    fun `save player id`() = runTest {
        savePlayerId.execute("123")
        assertEquals(123L, settingsRepository.playerId)
    }

    @Test
    fun `don't save empty player id`() = runTest {
        savePlayerId.execute("")
        assertEquals(-1L, settingsRepository.playerId)
    }
}
