/*
 *    Copyright 2023 Andrey Mukamolov
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

package dev.fobo66.wearmmr.companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.fobo66.wearmmr.companion.ui.theme.WearMMRTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearMMRTheme {
                // A surface container using the 'background' color from the theme
                Scaffold { padding ->
                    Login(modifier = Modifier.padding(padding))
                }
            }
        }
    }
}

@Composable
fun Login(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Button(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.Center)) {
            Text(text = "Sign in with Steam")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WearMMRTheme {
        Login()
    }
}
