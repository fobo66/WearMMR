package io.github.fobo66.wearmmr.domain

import android.app.PendingIntent
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.MonochromaticImage
import androidx.wear.watchface.complications.data.NoDataComplicationData
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import androidx.wear.watchface.complications.datasource.SuspendingComplicationDataSourceService
import io.github.fobo66.wearmmr.R
import io.github.fobo66.wearmmr.domain.usecase.RatingComplicationUseCase
import io.github.fobo66.wearmmr.ui.MainActivity
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
                .setMonochromaticImage(
                    MonochromaticImage.Builder(
                        Icon.createWithResource(
                            applicationContext,
                            R.drawable.ic_rating
                        )
                    )
                        .build()
                )
                .setTapAction(
                    PendingIntent.getActivity(
                        applicationContext,
                        MainActivity.REQUEST_CODE,
                        Intent(applicationContext, MainActivity::class.java),
                        PendingIntent.FLAG_IMMUTABLE
                    )
                )
                .build()
        } else {
            NoDataComplicationData()
        }
    }
}
