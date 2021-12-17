package io.github.fobo66.wearmmr.domain.usecase

import android.content.SharedPreferences
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.github.fobo66.wearmmr.api.MatchmakingRatingApi
import io.github.fobo66.wearmmr.db.MatchmakingDatabase
import io.github.fobo66.wearmmr.entities.toMatchmakingRating
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

class RatingComplicationUseCase(
    private val db: MatchmakingDatabase,
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
            db.gameStatsDao().insertRating(rating)
            rating.rating
        } catch (e: HttpException) {
            Timber.e(e, "Failed to load rating")
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }

    companion object {
        private const val KEY_PLAYER_ID = "playerId"
        private const val NO_PLAYER_ID = -1L
    }
}