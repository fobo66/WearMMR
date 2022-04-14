package io.github.fobo66.wearmmr.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.SurfaceHolder
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository
import java.io.PrintWriter
import java.time.ZonedDateTime

class MatchmakingRatingWatchFaceRenderer(
    private val context: Context,
    surfaceHolder: SurfaceHolder,
    watchState: WatchState,
    private val complicationSlotsManager: ComplicationSlotsManager,
    currentUserStyleRepository: CurrentUserStyleRepository,
    canvasType: Int
) : Renderer.CanvasRenderer2<Renderer.SharedAssets>(
    surfaceHolder,
    currentUserStyleRepository,
    watchState,
    canvasType,
    16L,
    true
) {
    override fun onDump(writer: PrintWriter) {
        TODO("Not yet implemented")
    }

    override suspend fun createSharedAssets(): SharedAssets =
        MatchmakingRatingSharedAssets(context)

    override fun render(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: SharedAssets
    ) {
        TODO("Not yet implemented")
    }

    override fun renderHighlightLayer(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: SharedAssets
    ) {
        TODO("Not yet implemented")
    }
}
