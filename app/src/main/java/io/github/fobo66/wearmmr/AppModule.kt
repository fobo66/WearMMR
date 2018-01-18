package io.github.fobo66.wearmmr

import android.content.Context
import org.koin.android.module.AndroidModule

/**
 * (c) 2017 Andrey Mukamolow <fobo66@protonmail.com>
 * Created 12/20/17.
 */
class AppModule(val appContext: Context) : AndroidModule() {

    override fun context() = applicationContext {
        provide { appContext }
    }
}