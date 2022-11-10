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
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.OutlinedCompactButton
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import coil.compose.AsyncImage
import io.github.fobo66.wearmmr.R
import io.github.fobo66.wearmmr.model.MainViewModel
import io.github.fobo66.wearmmr.model.MainViewState
import io.github.fobo66.wearmmr.ui.theme.WearMMRTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearMMRTheme {
                Scaffold(
                    timeText = {
                        TimeText()
                    }
                ) {
                    val viewModel: MainViewModel = koinViewModel()
                    val context = LocalContext.current

                    LaunchedEffect(Unit) {
                        viewModel.checkViewState()
                    }

                    val viewState by viewModel.state.collectAsStateWithLifecycle(initialValue = MainViewState.Loading)
                    MainContent(viewState, {
                        SettingsActivity.start(context)
                    })
                }
            }
        }
    }
}

@Composable
fun MainContent(
    viewState: MainViewState,
    goToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Crossfade(viewState, modifier = modifier) {
        when (it) {
            MainViewState.Loading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            is MainViewState.LoadedRating -> {
                RatingDetails(viewState = it, goToSettings)
            }

            MainViewState.FirstLaunch -> {
                FirstLaunch(goToSettings)
            }

            MainViewState.InvalidPlayerId -> {
                ErrorPrompt(
                    errorLabel = stringResource(id = R.string.invalid_player_id_error)
                )
            }

            MainViewState.NoRating -> {
                ErrorPrompt(
                    errorLabel = stringResource(id = R.string.no_rating_error)
                )
            }
        }
    }
}

@Composable
private fun FirstLaunch(goToSettings: () -> Unit, modifier: Modifier = Modifier) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.set_playerid_message),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Button(
                onClick = goToSettings,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(id = R.string.set_playerid_button_label))
            }
        }
    }
}

@Composable
fun RatingDetails(
    viewState: MainViewState.LoadedRating,
    goToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Column {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                AsyncImage(
                    model = viewState.avatarUrl,
                    contentDescription = stringResource(id = R.string.profile_picture_content_desc),
                    placeholder = painterResource(id = R.drawable.ic_person),
                    fallback = painterResource(id = R.drawable.ic_person),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(48.dp)
                        .clip(CircleShape)
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = viewState.playerName,
                        style = MaterialTheme.typography.title1,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = viewState.personaName,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            Text(
                text = viewState.rating, style = MaterialTheme.typography.display1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            OutlinedCompactButton(
                onClick = goToSettings,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = stringResource(
                        id = R.string.settings_button_label
                    )
                )
            }
        }
    }
}

@Composable
fun ErrorPrompt(errorLabel: String, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = errorLabel,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun MainPreview() {
    WearMMRTheme {
        MainContent(
            viewState = MainViewState.LoadedRating("test", "test", "1234", ""),
            goToSettings = {})
    }
}
