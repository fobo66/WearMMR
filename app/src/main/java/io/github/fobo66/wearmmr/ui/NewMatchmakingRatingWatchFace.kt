package io.github.fobo66.wearmmr.ui

import android.view.SurfaceHolder
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository

class NewMatchmakingRatingWatchFace : WatchFaceService() {
    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {
        val renderer = MatchmakingRatingWatchFaceRenderer(
            applicationContext,
            surfaceHolder,
            watchState,
            complicationSlotsManager,
            currentUserStyleRepository,
            CanvasType.HARDWARE
        )

        return WatchFace(
            WatchFaceType.DIGITAL,
            renderer
        )
    }
}
