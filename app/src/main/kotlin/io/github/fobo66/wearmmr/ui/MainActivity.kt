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

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.google.android.material.color.DynamicColors
import io.github.fobo66.wearmmr.R
import io.github.fobo66.wearmmr.databinding.ActivityMainBinding
import io.github.fobo66.wearmmr.model.MainViewModel
import io.github.fobo66.wearmmr.model.MainViewState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyToActivityIfAvailable(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomActionDrawer.setOnMenuItemClickListener {
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
                    when (it) {
                        MainViewState.FirstLaunch -> {
                            goToSettings()
                        }

                        is MainViewState.LoadedRating -> {
                            showRating(it)
                        }

                        MainViewState.Loading -> {
                            showProgress()
                        }

                        MainViewState.NoRating -> {
                            showError(R.string.no_rating_error)
                        }

                        MainViewState.InvalidPlayerId -> {
                            showError(R.string.invalid_player_id_error)
                        }
                    }
                }
            }
        }
    }

    private fun showRating(rating: MainViewState.LoadedRating) = binding.content.apply {
        progressBar.isVisible = false
        error.isVisible = false
        playerDetails.isVisible = true
        playerName.text = rating.playerName
        playerPersonaName.text = getString(
            R.string.player_name_display_placeholder,
            rating.personaName
        )
        this.rating.text = rating.rating
        playerPic.load(rating.avatarUrl) {
            fallback(R.drawable.ic_person)
            placeholder(R.drawable.ic_person)
        }
    }

    private fun showProgress() = binding.content.apply {
        playerDetails.isVisible = false
        error.isVisible = false
        progressBar.isVisible = true
    }

    private fun showError(@StringRes errorMessage: Int) = binding.content.apply {
        playerDetails.isVisible = false
        error.isVisible = true
        progressBar.isVisible = false

        error.setText(errorMessage)
    }

    private fun goToSettings() {
        Toast.makeText(
            this,
            R.string.set_playerid_message,
            Toast.LENGTH_SHORT
        ).show()
        SettingsActivity.start(this)
    }

    override fun onResume() {
        super.onResume()

        viewModel.checkViewState()
    }

    companion object {
        const val REQUEST_CODE = 1245
    }
}
