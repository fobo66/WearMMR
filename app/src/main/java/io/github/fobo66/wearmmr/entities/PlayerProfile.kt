/*
 * Copyright 2018. Andrey Mukamolov <fobo66@protonmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.fobo66.wearmmr.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * (c) 2017 Andrey Mukamolov <fobo66@protonmail.com>
 * Created 12/17/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
    "cheese", "avatarmedium", "avatarfull", "steamid", "profileurl", "last_login",
    "loccountrycode", "is_contributor"
)
data class PlayerProfile(
    @JsonProperty("account_id") val accountId: Long,
    @JsonProperty("name") val name: String?,
    @JsonProperty("personaname") val personaName: String?,
    @JsonProperty("avatar") val avatarUrl: String
)