package io.github.fobo66.data.source

import androidx.datastore.core.Serializer
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import java.io.InputStream
import java.io.OutputStream

class TestPreferenceSerializer : Serializer<Preferences> {
    override val defaultValue: Preferences
        get() = emptyPreferences()

    override suspend fun readFrom(input: InputStream): Preferences = defaultValue

    override suspend fun writeTo(t: Preferences, output: OutputStream) = Unit
}
