package io.github.fobo66.wearmmr.domain

import io.github.fobo66.wearmmr.domain.usecase.RatingComplicationUseCase
import org.koin.dsl.module

val domainModule = module {
    single {
        RatingComplicationUseCase(
            get()
        )
    }
}
