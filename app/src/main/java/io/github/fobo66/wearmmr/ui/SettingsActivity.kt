package io.github.fobo66.wearmmr.ui

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.BufferType.EDITABLE
import butterknife.BindView
import butterknife.ButterKnife
import io.github.fobo66.wearmmr.R
import org.jetbrains.anko.defaultSharedPreferences

class SettingsActivity : WearableActivity() {

  @BindView(R.id.player_id_input) lateinit var playerIdInput: EditText

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)
    ButterKnife.bind(this)

    // Enables Always-on
    setAmbientEnabled()

    playerIdInput.setText(defaultSharedPreferences.getInt("playerId", 0).toString(), EDITABLE)

    playerIdInput.setOnEditorActionListener({ _, actionId, _ ->
      if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
        val playerId: Int = Integer.parseInt(playerIdInput.text.toString())
        this.defaultSharedPreferences.edit().putInt("playerId", playerId).apply()
        return@setOnEditorActionListener true
      }
      false
    })
  }
}
