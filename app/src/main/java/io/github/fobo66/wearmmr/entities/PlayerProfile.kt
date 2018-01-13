package io.github.fobo66.wearmmr.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * (c) 2017 Andrey Mukamolow <fobo66@protonmail.com>
 * Created 12/17/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties("cheese", "avatarmedium", "avatarfull", "steamid", "profileurl", "last_login",
        "loccountrycode")
data class PlayerProfile(
    @JsonProperty("account_id") val accountId: Int,
    val name: String,
    @JsonProperty("personaname") val personaName: String,
    @JsonProperty("avatar") val avatarUrl: String
)