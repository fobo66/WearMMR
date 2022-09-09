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
