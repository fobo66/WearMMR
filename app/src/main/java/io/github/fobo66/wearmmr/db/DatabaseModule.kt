package io.github.fobo66.wearmmr.db

import android.arch.persistence.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

/**
 * (c) 2018 Andrey Mukamolow <fobo66@protonmail.com>
 * Created 2/20/18.
 */

val databaseModule = applicationContext {
    provide {
        Room.databaseBuilder(
            androidApplication(),
            MatchmakingDatabase::class.java,
            "matchmaking"
        )
            .build()
    }
}