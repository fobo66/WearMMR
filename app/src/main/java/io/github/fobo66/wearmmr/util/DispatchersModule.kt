package io.github.fobo66.wearmmr.util

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val dispatchersModule = module {

    factory(qualifier("main")) {
        Dispatchers.Main
    }

    factory(qualifier("io")) {
        Dispatchers.IO
    }

    factory(qualifier("default")) {
        Dispatchers.Default
    }
}
