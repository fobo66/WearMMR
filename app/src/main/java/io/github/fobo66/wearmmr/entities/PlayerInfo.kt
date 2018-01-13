package io.github.fobo66.wearmmr.entities

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * (c) 2017 Andrey Mukamolow <fobo66@protonmail.com>
 * Created 12/17/17.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class PlayerInfo(
    val profile: PlayerProfile,
    @JsonProperty("rank_tier") val rankTier: Int?,
    @JsonProperty("mmr_estimate") val mmrEstimate: MmrEstimate

)