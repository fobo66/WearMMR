package io.github.fobo66.domain.usecase

interface LoadPlayerId {
    suspend fun execute(): String
}
