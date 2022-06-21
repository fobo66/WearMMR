package io.github.fobo66.data.di

import io.github.fobo66.data.source.PreferenceDataSource
import io.github.fobo66.data.source.PreferenceDataSourceImpl
import org.koin.dsl.module

val dataModule = module {
    single<PreferenceDataSource> {
        PreferenceDataSourceImpl()
    }
}
