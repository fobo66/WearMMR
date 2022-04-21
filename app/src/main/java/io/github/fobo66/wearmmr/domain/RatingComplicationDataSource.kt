package io.github.fobo66.wearmmr.domain

import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceService
import androidx.wear.watchface.complications.datasource.ComplicationRequest

class RatingComplicationDataSource : ComplicationDataSourceService() {
    override fun getPreviewData(type: ComplicationType): ComplicationData? {
        return ShortTextComplicationData.Builder(
            PlainComplicationText.Builder("4096").build(),
            PlainComplicationText.Builder("MMR").build()
        )
            .build()
    }

    override fun onComplicationRequest(
        request: ComplicationRequest,
        listener: ComplicationRequestListener
    ) {
        TODO("Not yet implemented")
    }
}
