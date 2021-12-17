/*
 * Copyright 2018. Andrey Mukamolov <fobo66@protonmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.fobo66.wearmmr

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.IBinder
import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationData.Builder
import android.support.wearable.complications.ComplicationManager
import android.support.wearable.complications.ComplicationProviderService
import android.support.wearable.complications.ComplicationText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ServiceLifecycleDispatcher
import androidx.lifecycle.lifecycleScope
import io.github.fobo66.wearmmr.domain.usecase.RatingComplicationUseCase
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

/**
 * Provides rating info for watchface
 */

class RatingComplicationProviderService : ComplicationProviderService(), LifecycleOwner {

    private val dispatcher = ServiceLifecycleDispatcher(this)

    private val ratingComplicationUseCase: RatingComplicationUseCase by inject()

    override fun getLifecycle(): Lifecycle = dispatcher.lifecycle

    override fun onCreate() {
        dispatcher.onServicePreSuperOnCreate()
        super.onCreate()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        dispatcher.onServicePreSuperOnStart()
        super.onStart(intent, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        dispatcher.onServicePreSuperOnBind()
        return super.onBind(intent)
    }

    override fun onComplicationUpdate(
        complicationId: Int, dataType: Int, complicationManager: ComplicationManager
    ) {
        updateRating(complicationManager, complicationId)
    }

    override fun onDestroy() {
        dispatcher.onServicePreSuperOnDestroy()
        super.onDestroy()
    }

    private fun updateRating(
        complicationManager: ComplicationManager,
        complicationId: Int
    ) {
        lifecycleScope.launch {
            val rating = ratingComplicationUseCase.execute()

            if (rating != null) {
                val complicationData: ComplicationData = Builder(
                    ComplicationData.TYPE_SHORT_TEXT
                )
                    .setIcon(
                        Icon.createWithResource(
                            applicationContext,
                            R.drawable.ic_rating
                        )
                    )
                    .setShortText(ComplicationText.plainText(rating.toString()))
                    .setImageContentDescription(
                        ComplicationText.plainText(
                            applicationContext.getText(
                                R.string.rating_complication_description
                            )
                        )
                    )
                    .build()

                complicationManager.updateComplicationData(
                    complicationId,
                    complicationData
                )
            } else {
                complicationManager.noUpdateRequired(complicationId)
            }
        }
    }
}
