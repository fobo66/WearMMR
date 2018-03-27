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

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.github.fobo66.wearmmr.entities.MatchmakingRating

/**
 * MMR Database
 * Created 12/20/17.
 */
@Database(entities = [(MatchmakingRating::class)], version = 1)
abstract class MatchmakingDatabase : RoomDatabase() {
    abstract fun gameStatsDao(): MatchmakingDao
}