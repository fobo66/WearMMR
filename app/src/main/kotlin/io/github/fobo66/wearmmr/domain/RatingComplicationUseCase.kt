package io.github.fobo66.wearmmr.domain

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.github.fobo66.data.repositories.RatingRepository
import io.github.fobo66.data.repositories.SettingsRepository
import okio.IOException
import timber.log.Timber

class RatingComplicationUseCase(
    private val settingsRepository: SettingsRepository,
    private val ratingRepository: RatingRepository
) {
    suspend fun execute(): Int? {
        return try {
            val playerId = settingsRepository.loadPlayerId()
            val rating = if (playerId > 0) {
                ratingRepository.fetchRating(playerId)
            } else {
                null
            }
            rating?.rating
        } catch (e: IOException) {
            Timber.e(e, "Failed to load rating")
            Firebase.crashlytics.recordException(e)
            null
        }
    }
}
