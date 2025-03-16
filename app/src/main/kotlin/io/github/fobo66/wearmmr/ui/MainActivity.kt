/*
 *    Copyright 2025 Andrey Mukamolov
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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.OutlinedCompactButton
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import androidx.wear.compose.ui.tooling.preview.WearPreviewLargeRound
import androidx.wear.compose.ui.tooling.preview.WearPreviewSmallRound
import androidx.wear.compose.ui.tooling.preview.WearPreviewSquare
import coil.compose.AsyncImage
import com.google.android.horologist.compose.layout.ResponsiveTimeText
import com.google.android.horologist.compose.layout.responsivePaddingDefaults
import io.github.fobo66.wearmmr.R
import io.github.fobo66.wearmmr.model.MainViewModel
import io.github.fobo66.wearmmr.model.MainViewState
import io.github.fobo66.wearmmr.ui.theme.WearMMRTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearMMRTheme {
                AppScaffold(
                    timeText = {
                        ResponsiveTimeText()
                    }
                ) {
                    val viewModel: MainViewModel = koinViewModel()
                    val context = LocalContext.current

                    LaunchedEffect(Unit) {
                        viewModel.checkViewState()
                    }

                    val viewState by viewModel.state.collectAsStateWithLifecycle(
                        initialValue = MainViewState.Loading
                    )
                    MainContent(viewState, {
                        SettingsActivity.start(context)
                    })
                }
            }
        }
    }
}

@Composable
fun MainContent(viewState: MainViewState, goToSettings: () -> Unit, modifier: Modifier = Modifier) {
    ScreenScaffold(modifier = modifier) { padding ->
        Crossfade(viewState, label = "MainContent", modifier = Modifier.padding(padding)) {
            when (it) {
                MainViewState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize()
                )

                is MainViewState.LoadedRating -> {
                    RatingDetails(
                        viewState = it,
                        goToSettings = goToSettings,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(responsivePaddingDefaults())
                    )
                }

                MainViewState.FirstLaunch -> {
                    FirstLaunch(
                        goToSettings,
                        modifier = Modifier
                            .fillMaxSize()
                    )
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
}

@Composable
fun FirstLaunch(goToSettings: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.set_playerid_message),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = goToSettings,
                modifier = Modifier
                    .fillMaxWidth()
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
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Column {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            ) {
                AsyncImage(
                    model = viewState.avatarUrl,
                    contentDescription = stringResource(id = R.string.profile_picture_content_desc),
                    placeholder = painterResource(id = R.drawable.ic_person),
                    fallback = painterResource(id = R.drawable.ic_person),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = viewState.playerName,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = viewState.personaName,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            Text(
                text = viewState.rating,
                style = MaterialTheme.typography.displaySmall,
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
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = errorLabel,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

class ViewStatePreviewProvider :
    CollectionPreviewParameterProvider<MainViewState>(
        listOf(
            MainViewState.FirstLaunch,
            MainViewState.NoRating,
            MainViewState.InvalidPlayerId,
            MainViewState.LoadedRating("test", "test", "1234", "")
        )
    )

@WearPreviewSmallRound
@WearPreviewLargeRound
@WearPreviewSquare
@Composable
private fun MainPreview(
    @PreviewParameter(provider = ViewStatePreviewProvider::class) state: MainViewState
) {
    WearMMRTheme {
        MainContent(
            viewState = state,
            goToSettings = {}
        )
    }
}
