package io.github.fobo66.wearmmr.db

import android.arch.persistence.room.Room
import android.content.Context
import org.koin.android.module.AndroidModule

/**
 * (c) 2017 Andrey Mukamolow <fobo66@protonmail.com>
 * Created 12/20/17.
 */
class DatabaseModule : AndroidModule() {
  override fun context() = applicationContext {
    provide { provideRoomDatabase(get()) }
  }

  private fun provideRoomDatabase(appContext: Context): MatchmakingDatabase {
    return Room.databaseBuilder(appContext, MatchmakingDatabase::class.java, "matchmaking")
        .build()
  }

}