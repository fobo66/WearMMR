package io.github.fobo66.wearmmr.model

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel

class MainViewModel(
    private val preferences: SharedPreferences
) : ViewModel() {

    fun checkFirstLaunch() {
        preferences.getBoolean("firstLaunch", true)
    }

}
