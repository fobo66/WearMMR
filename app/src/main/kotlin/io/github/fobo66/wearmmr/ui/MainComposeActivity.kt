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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import io.github.fobo66.wearmmr.ui.theme.WearMMRTheme

class MainComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearMMRTheme {
                Scaffold(
                    timeText = {
                        TimeText()
                    }
                ) {
                    MainContent(name = "Android")
                }
            }
        }
    }
}

@Composable
fun MainContent(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Hello $name!")
        Text(text = "Player name")
        Text(text = "1234", style = MaterialTheme.typography.title1)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WearMMRTheme {
        MainContent("Android")
    }
}
