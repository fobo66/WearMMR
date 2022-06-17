package io.github.fobo66.data.source

interface PreferenceDataSource {
    suspend fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    suspend fun getLong(key: String, defaultValue: Long = 0L): Long
}
