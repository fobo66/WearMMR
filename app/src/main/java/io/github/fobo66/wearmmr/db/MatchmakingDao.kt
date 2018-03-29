/*
 * Copyright 2018. Andrey Mukamolow <fobo66@protonmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.fobo66.wearmmr.db

import android.arch.persistence.room.*
import io.github.fobo66.wearmmr.entities.MatchmakingRating
import io.reactivex.Flowable

/**
 * DAO for MMR
 *
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