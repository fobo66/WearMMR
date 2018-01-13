package io.github.fobo66.wearmmr.entities

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MmrEstimate(
    @JsonProperty("estimate") val estimate: Int?
)
