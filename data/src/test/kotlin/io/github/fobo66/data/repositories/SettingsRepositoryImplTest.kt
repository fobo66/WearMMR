/*
 *    Copyright 2022 Andrey Mukamolov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.fobo66.data.repositories

import io.github.fobo66.data.fake.FakePreferenceDataSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

class SettingsRepositoryImplTest {

    private val preferenceDataSource = FakePreferenceDataSource()

    private val settingsRepository: SettingsRepository =
        SettingsRepositoryImpl(preferenceDataSource)

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
