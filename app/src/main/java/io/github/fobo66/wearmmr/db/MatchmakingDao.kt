/*
 * Copyright 2018. Andrey Mukamolov <fobo66@protonmail.com>
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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.fobo66.wearmmr.entities.MatchmakingRating

/**
 * DAO for MMR
 *
 * Created 12/20/17.
 */
@Dao
interface MatchmakingDao {
    @Query("SELECT * FROM mmr WHERE playerId = :playerId LIMIT 1")
    suspend fun findOneByPlayerId(playerId: Long): MatchmakingRating?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRating(rating: MatchmakingRating)
}
