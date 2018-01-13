package io.github.fobo66.wearmmr.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.github.fobo66.wearmmr.entities.MatchmakingRating
import io.reactivex.Flowable

/**
 * (c) 2017 Andrey Mukamolow <fobo66@protonmail.com>
 * Created 12/20/17.
 */
@Dao
interface MatchmakingDao {
  @Query("SELECT * FROM mmr WHERE playerId LIKE :playerId LIMIT 1")
  fun findOneByPlayerId(playerId: Int): Flowable<MatchmakingRating>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertRating(rating: MatchmakingRating)

  @Update
  fun updateRatings(vararg ratings: MatchmakingRating)
}