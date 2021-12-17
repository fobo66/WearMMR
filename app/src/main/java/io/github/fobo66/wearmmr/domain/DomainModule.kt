package io.github.fobo66.wearmmr.domain

import androidx.preference.PreferenceManager
import io.github.fobo66.wearmmr.domain.usecase.RatingComplicationUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val domainModule = module {
    single {
        RatingComplicationUseCase(
            get(),
            PreferenceManager.getDefaultSharedPreferences(androidApplication()),
            get(),
            get(qualifier("io"))
        )
    }
}
