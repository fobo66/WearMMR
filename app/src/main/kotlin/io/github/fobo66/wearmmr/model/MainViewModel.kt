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

package io.github.fobo66.wearmmr.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.fobo66.domain.entities.RatingState
import io.github.fobo66.domain.usecase.ResolveRatingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val resolveRatingState: ResolveRatingState
) : ViewModel() {

    private val _state: MutableStateFlow<MainViewState> = MutableStateFlow(MainViewState.Loading)
    val state: StateFlow<MainViewState>
        get() = _state

    fun checkViewState() = viewModelScope.launch {
        val viewState = when (val state = resolveRatingState.execute()) {
            is RatingState.LoadedRating -> MainViewState.LoadedRating(
                state.playerName,
                state.personaName,
                state.rating,
                state.avatarUrl
            )
            RatingState.NoPlayerId -> MainViewState.FirstLaunch
            RatingState.NoRating -> MainViewState.NoRating
        }

        _state.emit(viewState)
    }
}
