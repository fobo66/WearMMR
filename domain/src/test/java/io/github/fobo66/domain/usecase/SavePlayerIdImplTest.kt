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
