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
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.wearable.complications.ProviderUpdateRequester
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.BufferType.EDITABLE
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import io.github.fobo66.wearmmr.RatingComplicationProviderService
import io.github.fobo66.wearmmr.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private val inputMethodManager: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private val updateRequester: ProviderUpdateRequester by lazy {
        ProviderUpdateRequester(
            this,
            ComponentName(this, RatingComplicationProviderService::class.java)
        )
    }

    private lateinit var defaultSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        binding.playerIdInput.setText(
            defaultSharedPreferences.getLong("playerId", 0).toString(),
            EDITABLE
        )

        binding.playerIdInput.setOnEditorActionListener { _, actionId, _ ->
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
        val playerIdString = binding.playerIdInput.text.toString()
        if (playerIdString.isNotBlank()) {
            val playerId: Long = playerIdString.toLong()
            this.defaultSharedPreferences.edit().putLong("playerId", playerId).apply()
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }
}
