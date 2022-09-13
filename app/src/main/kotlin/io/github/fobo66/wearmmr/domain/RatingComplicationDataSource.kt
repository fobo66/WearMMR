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
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.github.fobo66.domain.usecase.RatingComplicationUseCase
import io.github.fobo66.wearmmr.R
import io.github.fobo66.wearmmr.ui.MainActivity
import java.io.IOException
import org.koin.android.ext.android.inject
import timber.log.Timber

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
        val rating = try {
            ratingComplicationUseCase.execute()
        } catch (exception: IOException) {
            Timber.e(exception, "Failed to fetch rating fro complication")
            Firebase.crashlytics.recordException(exception)
            null
        }

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
