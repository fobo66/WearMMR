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
