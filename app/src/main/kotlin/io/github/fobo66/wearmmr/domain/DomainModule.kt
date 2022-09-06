package io.github.fobo66.wearmmr.domain

import org.koin.dsl.module

val domainModule = module {
    single {
        RatingComplicationUseCase(
            get()
        )
    }
}
