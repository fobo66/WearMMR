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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import coil.compose.AsyncImage
import io.github.fobo66.wearmmr.R
import io.github.fobo66.wearmmr.model.MainViewModel
import io.github.fobo66.wearmmr.model.MainViewState
import io.github.fobo66.wearmmr.ui.theme.WearMMRTheme
import org.koin.androidx.compose.koinViewModel

class MainComposeActivity : ComponentActivity() {
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

                    val viewState by viewModel.state.collectAsStateWithLifecycle(initialValue = MainViewState.Loading)
                    MainContent(viewState)
                }
            }
        }
    }
}

@Composable
fun MainContent(viewState: MainViewState, modifier: Modifier = Modifier) {
    when (viewState) {
        MainViewState.Loading -> CircularProgressIndicator()
        is MainViewState.LoadedRating -> {
            Column(modifier = modifier) {
                AsyncImage(
                    model = viewState.avatarUrl,
                    contentDescription = stringResource(id = R.string.profile_picture_content_desc),
                    placeholder = painterResource(id = R.drawable.ic_person),
                    fallback = painterResource(id = R.drawable.ic_person)
                )
                Text(text = viewState.playerName)
                Text(text = viewState.personaName)
                Text(text = viewState.rating, style = MaterialTheme.typography.display3)
            }
        }

        MainViewState.FirstLaunch -> {
            Column(modifier = modifier) {
                Text(text = stringResource(id = R.string.set_playerid_message))
                Button(onClick = { }) {
                    Text(text = stringResource(id = R.string.set_playerid_button_label))
                }
            }
        }

        MainViewState.InvalidPlayerId -> {
            ErrorPrompt(
                errorLabel = stringResource(id = R.string.invalid_player_id_error),
                modifier
            )
        }

        MainViewState.NoRating -> {
            ErrorPrompt(errorLabel = stringResource(id = R.string.no_rating_error), modifier)
        }
    }
}

@Composable
fun ErrorPrompt(errorLabel: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = errorLabel,
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WearMMRTheme {
        MainContent(MainViewState.Loading)
    }
}
