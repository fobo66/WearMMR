package io.github.fobo66.domain.di

import io.github.fobo66.data.db.databaseModule
import io.github.fobo66.data.di.dataModule
import io.github.fobo66.data.di.dispatchersModule
import io.github.fobo66.domain.usecase.LoadPlayerId
import io.github.fobo66.domain.usecase.LoadPlayerIdImpl
import io.github.fobo66.domain.usecase.RatingComplicationUseCase
import io.github.fobo66.domain.usecase.RatingComplicationUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    includes(databaseModule, dataModule, dispatchersModule)
    single<RatingComplicationUseCase> {
        RatingComplicationUseCaseImpl(
            get()
        )
    }
    single<LoadPlayerId> {
        LoadPlayerIdImpl(get())
    }
}
