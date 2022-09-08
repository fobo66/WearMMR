package io.github.fobo66.domain.usecase

import io.github.fobo66.domain.fake.FakeSettingsRepository
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class LoadPlayerIdImplTest {

    private val settingsRepository = FakeSettingsRepository()

    private lateinit var loadPlayerId: LoadPlayerId

    @BeforeTest
    fun setUp() {
        loadPlayerId = LoadPlayerIdImpl(settingsRepository)
    }

    @AfterTest
    fun tearDown() {
        settingsRepository.clear()
    }

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
