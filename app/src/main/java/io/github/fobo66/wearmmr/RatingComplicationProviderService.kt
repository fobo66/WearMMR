/*
 * Copyright 2018. Andrey Mukamolow <fobo66@protonmail.com>
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

import android.graphics.drawable.Icon
import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationData.Builder
import android.support.wearable.complications.ComplicationManager
import android.support.wearable.complications.ComplicationProviderService
import android.support.wearable.complications.ComplicationText
import android.util.Log
import io.github.fobo66.wearmmr.R.drawable
import io.github.fobo66.wearmmr.R.string
import io.github.fobo66.wearmmr.api.MatchmakingRatingApi
import io.github.fobo66.wearmmr.db.MatchmakingDatabase
import io.github.fobo66.wearmmr.entities.MatchmakingRating
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.defaultSharedPreferences
import org.koin.android.ext.android.inject

/**
 * Provides rating info for watchface
 */

class RatingComplicationProviderService : ComplicationProviderService() {

    private val noPlayerId = -1

    private val disposables = CompositeDisposable()

    val matchmakingRatingClient: MatchmakingRatingApi by inject()
    val db: MatchmakingDatabase by inject()

    override fun onComplicationUpdate(
        complicationId: Int, dataType: Int, complicationManager: ComplicationManager?
    ) {
        updateRating(complicationManager, complicationId)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun updateRating(
        complicationManager: ComplicationManager?,
        complicationId: Int
    ) {
        val playerId = defaultSharedPreferences.getInt("playerId", noPlayerId)

        if (playerId != noPlayerId) {
            disposables.add(
                matchmakingRatingClient.fetchPlayerProfile(playerId)
                    .subscribeOn(Schedulers.io())
                    .map { playerInfo ->
                        MatchmakingRating(
                            playerInfo.profile.accountId, playerInfo.profile.name,
                            playerInfo.profile.personaName, playerInfo.profile.avatarUrl,
                            playerInfo.mmrEstimate.estimate
                        )
                    }
                    .flatMap { rating ->
                        db.gameStatsDao().insertRating(rating)
                        return@flatMap Observable.just(rating.rating)
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            val complicationData: ComplicationData = Builder(
                                ComplicationData.TYPE_SHORT_TEXT
                            )
                                .setIcon(
                                    Icon.createWithResource(
                                        applicationContext,
                                        drawable.ic_rating
                                    )
                                )
                                .setShortText(ComplicationText.plainText(it.toString()))
                                .setImageContentDescription(
                                    ComplicationText.plainText(
                                        applicationContext.getText(string.rating_complication_description)
                                    )
                                )
                                .build()

                            complicationManager!!.updateComplicationData(
                                complicationId,
                                complicationData
                            )
                        },
                        { error ->
                            Log.e(javaClass.name, "Error while updating player info", error)
                            complicationManager!!.noUpdateRequired(complicationId)
                        })
            )
        } else {
            complicationManager!!.noUpdateRequired(complicationId)
        }
    }
}
