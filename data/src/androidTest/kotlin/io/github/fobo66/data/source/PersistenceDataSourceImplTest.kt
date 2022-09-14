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

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.fobo66.data.db.MatchmakingDatabase
import io.github.fobo66.data.entities.MatchmakingRating
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class PersistenceDataSourceImplTest {
    private lateinit var persistenceDataSource: PersistenceDataSource
    private lateinit var db: MatchmakingDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MatchmakingDatabase::class.java).build()
        persistenceDataSource = PersistenceDataSourceImpl(db.gameStatsDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun loadRating() = runTest {
        assertNull(persistenceDataSource.loadRating(0L))
    }

    @Test
    fun saveRating() = runTest {
        val rating = MatchmakingRating(0L, "test", "test", "test", 0)
        val id = persistenceDataSource.saveRating(rating)
        assertNotNull(persistenceDataSource.loadRating(id))
    }
}
