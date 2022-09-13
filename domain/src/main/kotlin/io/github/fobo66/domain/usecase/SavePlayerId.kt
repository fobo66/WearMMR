package io.github.fobo66.domain.usecase

interface SavePlayerId {
    suspend fun execute(newPlayerId: String)
}
