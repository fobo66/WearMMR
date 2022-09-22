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

package io.github.fobo66.wearmmr.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.BufferType.EDITABLE
import androidx.activity.ComponentActivity
import androidx.core.content.getSystemService
import androidx.lifecycle.lifecycleScope
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceUpdateRequester
import com.google.android.material.color.DynamicColors
import io.github.fobo66.wearmmr.databinding.ActivitySettingsBinding
import io.github.fobo66.wearmmr.domain.RatingComplicationDataSource
import io.github.fobo66.wearmmr.model.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : ComponentActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private val updateRequester: ComplicationDataSourceUpdateRequester by lazy {
        ComplicationDataSourceUpdateRequester.create(
            this,
            ComponentName(this, RatingComplicationDataSource::class.java)
        )
    }

    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyToActivityIfAvailable(this)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenResumed {
            binding.playerIdInput.editText?.setText(
                settingsViewModel.loadPlayerId(),
                EDITABLE
            )
        }

        binding.playerIdInput.editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(
                    currentFocus!!.windowToken,
                    0
                )
                updatePlayerIdPreference()
                updateComplication()
                true
            } else {
                false
            }
        }
    }

    private fun updateComplication() {
        updateRequester.requestUpdateAll()
    }

    private fun updatePlayerIdPreference() {
        val playerIdString = binding.playerIdInput.editText?.text.toString()
        if (playerIdString.isNotBlank()) {
            settingsViewModel.savePlayerId(playerIdString)
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }
}
