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

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.support.wearable.complications.ProviderUpdateRequester
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView.BufferType.EDITABLE
import butterknife.BindView
import butterknife.ButterKnife
import io.github.fobo66.wearmmr.R
import io.github.fobo66.wearmmr.RatingComplicationProviderService
import org.jetbrains.anko.defaultSharedPreferences

class SettingsActivity : WearableActivity() {

    @BindView(R.id.player_id_input)
    lateinit var playerIdInput: EditText

    private val inputMethodManager: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private val updateRequester: ProviderUpdateRequester by lazy {
        ProviderUpdateRequester(
            this,
            ComponentName(this, RatingComplicationProviderService::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        ButterKnife.bind(this)

        // Enables Always-on
        setAmbientEnabled()

        playerIdInput.setText(defaultSharedPreferences.getLong("playerId", 0).toString(), EDITABLE)

        playerIdInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                updatePlayerIdPreference()
                updateComplication()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun updateComplication() {
        updateRequester.requestUpdateAll()
    }

    private fun updatePlayerIdPreference() {
        val playerId: Long = playerIdInput.text.toString().toLong()
        this.defaultSharedPreferences.edit().putLong("playerId", playerId).apply()
    }
}
