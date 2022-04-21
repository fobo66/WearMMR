package io.github.fobo66.wearmmr.domain

import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.NoDataComplicationData
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import androidx.wear.watchface.complications.datasource.SuspendingComplicationDataSourceService
import io.github.fobo66.wearmmr.domain.usecase.RatingComplicationUseCase
import org.koin.android.ext.android.inject

class RatingComplicationDataSource : SuspendingComplicationDataSourceService() {

    private val ratingComplicationUseCase: RatingComplicationUseCase by inject()

    override fun getPreviewData(type: ComplicationType): ComplicationData {
        return ShortTextComplicationData.Builder(
            PlainComplicationText.Builder("4096").build(),
            PlainComplicationText.Builder("MMR").build()
        )
            .build()
    }

    override suspend fun onComplicationRequest(request: ComplicationRequest): ComplicationData {
        val rating = ratingComplicationUseCase.execute()

        return if (rating != null) {
            ShortTextComplicationData.Builder(
                PlainComplicationText.Builder(rating.toString())
                    .build(),
                PlainComplicationText.Builder("MMR").build()
            )
                .build()
        } else {
            NoDataComplicationData()
        }
    }
}
