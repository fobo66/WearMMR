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
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.BufferType.EDITABLE
import androidx.activity.ComponentActivity
import androidx.core.content.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.wear.phone.interactions.authentication.CodeChallenge
import androidx.wear.phone.interactions.authentication.CodeVerifier
import androidx.wear.phone.interactions.authentication.OAuthRequest
import androidx.wear.phone.interactions.authentication.OAuthResponse
import androidx.wear.phone.interactions.authentication.RemoteAuthClient
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceUpdateRequester
import com.google.android.material.color.DynamicColors
import io.github.fobo66.wearmmr.databinding.ActivitySettingsBinding
import io.github.fobo66.wearmmr.domain.RatingComplicationDataSource
import io.github.fobo66.wearmmr.model.SettingsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                binding.playerIdInput.editText?.setText(
                    settingsViewModel.loadPlayerId(),
                    EDITABLE
                )
            }
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

        binding.settingsPlayerIdHint.setOnClickListener {
            val codeVerifier = CodeVerifier()
            val request = OAuthRequest.Builder(this.applicationContext)
                .setAuthProviderUrl(Uri.parse("https://steamcommunity.com/openid"))
                .setCodeChallenge(CodeChallenge(codeVerifier))
                .build()
            val client = RemoteAuthClient.create(this)
            client.sendAuthorizationRequest(request,
                { command -> command?.run() },
                object : RemoteAuthClient.Callback() {
                    override fun onAuthorizationError(request: OAuthRequest, errorCode: Int) {
                        Timber.e("Auth failed with code %d", errorCode)
                    }

                    override fun onAuthorizationResponse(
                        request: OAuthRequest,
                        response: OAuthResponse
                    ) {
                        Timber.d("Auth successful")
                    }
                }
            )


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
            context.startActivity(Intent(context, SettingsActivity::class.java).setClassName(
                context.packageName,
                this::class.java.name
            ))
        }
    }
}
