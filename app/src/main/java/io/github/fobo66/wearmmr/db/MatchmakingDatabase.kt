package io.github.fobo66.wearmmr.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.github.fobo66.wearmmr.entities.MatchmakingRating

/**
 * (c) 2017 Andrey Mukamolow <fobo66@protonmail.com>
 * Created 12/20/17.
 */
@Database(entities = [(MatchmakingRating::class)], version = 1)
abstract class MatchmakingDatabase : RoomDatabase() {
    abstract fun gameStatsDao(): MatchmakingDao
}