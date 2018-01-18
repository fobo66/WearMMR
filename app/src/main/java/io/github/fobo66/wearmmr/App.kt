package io.github.fobo66.wearmmr

import android.app.Application
import io.github.fobo66.wearmmr.api.ApiModule
import io.github.fobo66.wearmmr.db.DatabaseModule
import org.koin.android.ext.android.startKoin

/**
 * (c) 2017 Andrey Mukamolow <fobo66@protonmail.com>
 * Created 12/18/17.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(AppModule(applicationContext), ApiModule(), DatabaseModule()))
    }
}