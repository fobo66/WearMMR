package io.github.fobo66.wearmmr.ui

import androidx.wear.watchface.complications.data.ComplicationType

sealed class ComplicationsConfig(val id: Int, val supportedTypes: List<ComplicationType>) {
    object Left : ComplicationsConfig(LEFT_COMPLICATION_ID, listOf(ComplicationType.SHORT_TEXT))
    object Right : ComplicationsConfig(RIGHT_COMPLICATION_ID, listOf(ComplicationType.SHORT_TEXT))

    companion object {
        private const val LEFT_COMPLICATION_ID = 100
        private const val RIGHT_COMPLICATION_ID = 101
    }
}
