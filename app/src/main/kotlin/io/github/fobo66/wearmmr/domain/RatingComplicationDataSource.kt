/*
 *    Copyright 2022 Andrey Mukamolov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.Firebase
import io.github.fobo66.domain.usecase.RatingComplicationUseCase
import io.github.fobo66.wearmmr.R
import io.github.fobo66.wearmmr.ui.MainActivity
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.io.IOException

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
                        REQUEST_CODE,
                        Intent(applicationContext, MainActivity::class.java),
                        PendingIntent.FLAG_IMMUTABLE
                    )
                )
                .build()
        } else {
            NoDataComplicationData()
        }
    }

    companion object {
        const val REQUEST_CODE = 1234
    }
}
