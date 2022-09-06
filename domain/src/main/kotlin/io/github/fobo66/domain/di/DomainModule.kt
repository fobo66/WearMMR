package io.github.fobo66.domain.di

import io.github.fobo66.data.db.databaseModule
import io.github.fobo66.data.di.dataModule
import io.github.fobo66.data.di.dispatchersModule
import io.github.fobo66.domain.usecase.RatingComplicationUseCase
import org.koin.dsl.module

val domainModule = module {
    includes(databaseModule, dataModule, dispatchersModule)
    single {
        RatingComplicationUseCase(
            get()
        )
    }
}
