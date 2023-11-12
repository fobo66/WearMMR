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

import androidx.wear.watchface.complications.data.ComplicationType

sealed class ComplicationsConfig(val id: Int, val supportedTypes: List<ComplicationType>) {
    data object Left : ComplicationsConfig(LEFT_COMPLICATION_ID, listOf(ComplicationType.SHORT_TEXT))
    data object Right : ComplicationsConfig(RIGHT_COMPLICATION_ID, listOf(ComplicationType.SHORT_TEXT))

    companion object {
        private const val LEFT_COMPLICATION_ID = 100
        private const val RIGHT_COMPLICATION_ID = 101
    }
}
