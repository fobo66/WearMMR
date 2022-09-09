package io.github.fobo66.domain.usecase

import io.github.fobo66.domain.fake.FakeSettingsRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class LoadPlayerIdImplTest {

    private val settingsRepository = FakeSettingsRepository()

    private val loadPlayerId: LoadPlayerId = LoadPlayerIdImpl(settingsRepository)

    @Test
    fun `no player id`() = runTest {
        val playerId = loadPlayerId.execute()
        assertEquals("", playerId)
    }

    @Test
    fun `player id`() = runTest {
        settingsRepository.playerId = 123L
        val playerId = loadPlayerId.execute()
        assertEquals("123", playerId)
    }
}
