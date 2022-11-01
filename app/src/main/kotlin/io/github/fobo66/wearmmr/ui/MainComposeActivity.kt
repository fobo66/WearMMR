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
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
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
                Text(text = viewState.playerName)
                Text(text = viewState.personaName)
                Text(text = viewState.rating, style = MaterialTheme.typography.title1)
            }
        }

        MainViewState.FirstLaunch -> TODO()
        MainViewState.InvalidPlayerId -> TODO()
        MainViewState.NoRating -> TODO()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WearMMRTheme {
        MainContent(MainViewState.Loading)
    }
}
