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

package io.github.fobo66.wearmmr.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.wear.widget.drawer.WearableActionDrawerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.github.fobo66.wearmmr.R
import io.github.fobo66.wearmmr.db.MatchmakingDatabase
import io.github.fobo66.wearmmr.util.GlideApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val db: MatchmakingDatabase by inject()

    @BindView(R.id.bottom_action_drawer)
    lateinit var navigationDrawer: WearableActionDrawerView

    @BindView(R.id.player_pic)
    lateinit var playerPic: ImageView


    @BindView(R.id.player_name)
    lateinit var playerName: TextView

    @BindView(R.id.player_persona_name)
    lateinit var playerPersonaName: TextView

    @BindView(R.id.rating)
    lateinit var rating: TextView

    private val noPlayerId = -1L

    private lateinit var defaultSharedPreferences: SharedPreferences

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        navigationDrawer.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> {
                    SettingsActivity.start(this)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val isFirstLaunch: Boolean = defaultSharedPreferences.getBoolean("firstLaunch", true)

        if (isFirstLaunch) {
            defaultSharedPreferences.edit().putBoolean("firstLaunch", false).apply()
            Toast.makeText(this, R.string.set_playerid_message, Toast.LENGTH_SHORT).show()
            SettingsActivity.start(this)
        } else {
            val playerId = defaultSharedPreferences.getLong("playerId", noPlayerId)

            if (playerId != noPlayerId) {
                disposables.add(
                        db.gameStatsDao().findOneByPlayerId(playerId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ matchmakingRating ->
                                    playerName.text = matchmakingRating.name
                                    playerPersonaName.text =
                                            String.format("(%s)", matchmakingRating.personaName)
                                    rating.text = matchmakingRating.rating.toString()

                                    GlideApp.with(this)
                                            .load(matchmakingRating.avatarUrl)
                                            .placeholder(R.drawable.ic_person)
                                            .into(playerPic)
                                }, { error ->
                                    Log.e(this.javaClass.name, "Cannot load data from database", error)
                                    FirebaseCrashlytics.getInstance().recordException(error)
                                })
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        disposables.clear()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
