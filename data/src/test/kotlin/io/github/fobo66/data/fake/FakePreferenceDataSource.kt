package io.github.fobo66.data.fake

import io.github.fobo66.data.source.PreferenceDataSource

class FakePreferenceDataSource : PreferenceDataSource {
    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return defaultValue
    }

    override suspend fun saveBoolean(key: String, value: Boolean) = Unit

    override suspend fun getLong(key: String, defaultValue: Long): Long {
        return defaultValue
    }

    override suspend fun saveLong(key: String, value: Long) = Unit
}
