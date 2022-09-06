package io.github.fobo66.wearmmr.domain

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.github.fobo66.data.repositories.RatingRepository
import okio.IOException
import timber.log.Timber

class RatingComplicationUseCase(
    private val ratingRepository: RatingRepository
) {
    suspend fun execute(): Int? {
        return try {
            val rating = ratingRepository.fetchRating()
            rating?.rating
        } catch (e: IOException) {
            Timber.e(e, "Failed to load rating")
            Firebase.crashlytics.recordException(e)
            null
        }
    }
}
