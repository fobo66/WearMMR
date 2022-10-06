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
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.color.DynamicColors
import io.github.fobo66.wearmmr.R
import io.github.fobo66.wearmmr.databinding.ActivityMainBinding
import io.github.fobo66.wearmmr.model.MainViewModel
import io.github.fobo66.wearmmr.model.MainViewState
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

        viewModel.checkViewState()

        lifecycleScope.launchWhenResumed {
            viewModel.state.collect {
                when (it) {
                    MainViewState.FirstLaunch -> {
                        Toast.makeText(
                            this@MainActivity,
                            R.string.set_playerid_message,
                            Toast.LENGTH_SHORT
                        ).show()
                        SettingsActivity.start(this@MainActivity)
                    }

                    is MainViewState.LoadedRating -> {
                        binding.content.progressBar.isVisible = false
                        binding.content.playerIdError.isVisible = false
                        binding.content.playerDetails.isVisible = true
                        binding.content.playerName.text = it.playerName
                        binding.content.playerPersonaName.text = getString(
                            R.string.player_name_display_placeholder,
                            it.personaName
                        )
                        binding.content.rating.text =
                            if (it.rating != "0") {
                                it.rating
                            } else {
                                getString(R.string.placeholder_rating)
                            }

                        binding.content.playerPic.load(it.avatarUrl) {
                            placeholder(R.drawable.ic_person)
                        }
                    }

                    MainViewState.Loading -> {
                        binding.content.playerDetails.isVisible = false
                        binding.content.playerIdError.isVisible = false
                        binding.content.progressBar.isVisible = true
                    }

                    MainViewState.NoRating -> {
                        binding.content.progressBar.isVisible = false
                        binding.content.playerIdError.isVisible = false
                        binding.content.playerDetails.isVisible = true
                        binding.content.playerName.text = ""
                        binding.content.playerPersonaName.text = ""
                        binding.content.playerPic.load(R.drawable.ic_person)
                        binding.content.rating.setText(R.string.placeholder_rating)
                    }

                    MainViewState.InvalidPlayerId -> {
                        binding.content.progressBar.isVisible = false
                        binding.content.playerDetails.isVisible = false
                        binding.content.playerIdError.isVisible = true
                    }
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE = 1245
    }
}
