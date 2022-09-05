package io.github.fobo66.data.fake

import io.github.fobo66.data.source.PreferenceDataSource

class FakePreferenceDataSource : PreferenceDataSource, Clearable {

    var longNumber: Long? = null
    var booleanValue: Boolean? = null
    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return booleanValue ?: defaultValue
    }

    override suspend fun saveBoolean(key: String, value: Boolean) {
        booleanValue = value
    }

    override suspend fun getLong(key: String, defaultValue: Long): Long {
        return longNumber ?: defaultValue
    }

    override suspend fun saveLong(key: String, value: Long) {
        longNumber = value
    }

    override fun clear() {
        longNumber = -1L
        booleanValue = true
    }
}
