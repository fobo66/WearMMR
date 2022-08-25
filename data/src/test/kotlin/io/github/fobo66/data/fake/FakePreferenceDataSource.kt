package io.github.fobo66.data.fake

import io.github.fobo66.data.source.PreferenceDataSource

class FakePreferenceDataSource : PreferenceDataSource, Clearable {

    var long = -1L
    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return defaultValue
    }

    override suspend fun saveBoolean(key: String, value: Boolean) = Unit

    override suspend fun getLong(key: String, defaultValue: Long): Long {
        return long
    }

    override suspend fun saveLong(key: String, value: Long) {
        long = value
    }

    override fun clear() {
        long = 0L
    }
}
