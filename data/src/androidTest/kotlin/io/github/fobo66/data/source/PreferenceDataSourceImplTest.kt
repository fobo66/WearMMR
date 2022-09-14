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

package io.github.fobo66.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class PreferenceDataSourceImplTest {
    private lateinit var preferenceDataSource: PreferenceDataSource
    private lateinit var dataStore: DataStore<Preferences>

    @get:Rule
    val tempFile = TemporaryFolder()

    @Before
    fun setUp() {
        dataStore = DataStoreFactory.create(TestPreferenceSerializer()) {
            tempFile.newFile()
        }
        preferenceDataSource = PreferenceDataSourceImpl(dataStore)
    }

    @Test
    fun getDefaultBoolean() = runTest {
        assertFalse(preferenceDataSource.getBoolean("test"))
    }

    @Test
    fun getDefaultLong() = runTest {
        assertEquals(0L, preferenceDataSource.getLong("test"))
    }

    @Test
    fun saveBoolean() = runTest {
        preferenceDataSource.saveBoolean("test", true)
        assertTrue(preferenceDataSource.getBoolean("test"))
    }

    @Test
    fun saveLong() = runTest {
        preferenceDataSource.saveLong("long", 1L)
        assertEquals(1L, preferenceDataSource.getLong("long"))
    }
}
