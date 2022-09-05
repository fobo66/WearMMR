package io.github.fobo66.data.repositories

import io.github.fobo66.data.fake.FakePreferenceDataSource
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

class SettingsRepositoryImplTest {

    private val preferenceDataSource = FakePreferenceDataSource()

    private lateinit var settingsRepository: SettingsRepository

    @BeforeTest
    fun setUp() {
        settingsRepository = SettingsRepositoryImpl(preferenceDataSource)
    }

    @AfterTest
    fun tearDown() {
        preferenceDataSource.clear()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load default player id`() = runTest {
        val id = settingsRepository.loadPlayerId()
        assertEquals(SettingsRepositoryImpl.NO_PLAYER_ID, id)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `save player id`() = runTest {
        val id = settingsRepository.loadPlayerId()
        settingsRepository.savePlayerId(2L)
        assertNotEquals(id, settingsRepository.loadPlayerId())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load first launch`() = runTest {
        assertTrue {
            settingsRepository.loadFirstLaunch()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `save first launch`() = runTest {
        settingsRepository.saveFirstLaunch(false)
        assertFalse {
            settingsRepository.loadFirstLaunch()
        }
    }
}
