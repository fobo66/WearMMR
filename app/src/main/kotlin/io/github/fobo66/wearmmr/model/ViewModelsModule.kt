package io.github.fobo66.wearmmr.model

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        MainViewModel(
            get(),
            get()
        )
    }
    viewModel {
        SettingsViewModel(get())
    }
}
