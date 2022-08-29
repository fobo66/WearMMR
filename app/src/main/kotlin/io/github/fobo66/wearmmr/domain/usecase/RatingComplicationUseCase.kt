package io.github.fobo66.wearmmr.domain.usecase

import android.content.SharedPreferences
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.github.fobo66.data.entities.toMatchmakingRating
import io.github.fobo66.data.source.PersistenceDataSource
import io.github.fobo66.wearmmr.api.MatchmakingRatingApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

class RatingComplicationUseCase(
    private val persistenceDataSource: PersistenceDataSource,
    private val preferences: SharedPreferences,
    private val matchmakingRatingClient: MatchmakingRatingApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun execute(): Int? {
        val playerId = preferences.getLong(KEY_PLAYER_ID, NO_PLAYER_ID)

        if (playerId == NO_PLAYER_ID) {
            return null
        }

        return try {
            val playerInfo = withContext(ioDispatcher) {
                matchmakingRatingClient.fetchPlayerProfile(playerId)
            }
            val rating = playerInfo.toMatchmakingRating()
            persistenceDataSource.saveRating(rating)
            rating.rating
        } catch (e: HttpException) {
            Timber.e(e, "Failed to load rating")
            Firebase.crashlytics.recordException(e)
            null
        }
    }

    companion object {
        private const val KEY_PLAYER_ID = "playerId"
        private const val NO_PLAYER_ID = -1L
    }
}
