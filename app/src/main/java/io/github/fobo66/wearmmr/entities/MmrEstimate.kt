package io.github.fobo66.wearmmr.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties("stdDev", "n") // this properties are present in API docs but not in actual response
data class MmrEstimate(
    @JsonProperty("estimate") val estimate: Int?
)
