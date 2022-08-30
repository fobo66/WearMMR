package io.github.fobo66.wearmmr.model

import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        MainViewModel(
            preferences = PreferenceManager.getDefaultSharedPreferences(
                androidContext()
            ),
            get()
        )
    }
    viewModel {
        SettingsViewModel(get())
    }
}
