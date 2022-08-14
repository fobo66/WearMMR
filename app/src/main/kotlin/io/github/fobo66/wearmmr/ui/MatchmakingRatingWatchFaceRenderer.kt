package io.github.fobo66.wearmmr.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.SurfaceHolder
import androidx.core.graphics.toRectF
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.DrawMode
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MatchmakingRatingWatchFaceRenderer(
    private val context: Context,
    surfaceHolder: SurfaceHolder,
    watchState: WatchState,
    private val complicationSlotsManager: ComplicationSlotsManager,
    currentUserStyleRepository: CurrentUserStyleRepository,
    canvasType: Int
) : Renderer.CanvasRenderer2<MatchmakingRatingSharedAssets>(
    surfaceHolder,
    currentUserStyleRepository,
    watchState,
    canvasType,
    DEFAULT_FRAME_UPDATE_DELAY_MS,
    true
) {
    private val timeFormat: String
        get() = if (isAmbient()) {
            TIME_FORMAT_AMBIENT
        } else {
            TIME_FORMAT_INTERACTIVE
        }

    private fun isAmbient() = renderParameters.drawMode == DrawMode.AMBIENT

    override suspend fun createSharedAssets(): MatchmakingRatingSharedAssets =
        MatchmakingRatingSharedAssets(context)

    override fun render(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: MatchmakingRatingSharedAssets
    ) {
        if (isAmbient() || sharedAssets.backgroundBitmap.isRecycled) {
            canvas.drawPaint(sharedAssets.backgroundPaint)
        } else {
            canvas.drawBitmap(
                sharedAssets.backgroundBitmap,
                null,
                bounds.toRectF(),
                sharedAssets.backgroundPaint
            )
        }

        canvas.drawText(
            zonedDateTime.format(DateTimeFormatter.ofPattern(timeFormat)),
            sharedAssets.timeXOffset,
            sharedAssets.timeYOffset,
            sharedAssets.textPaint
        )

        for ((_, complication) in complicationSlotsManager.complicationSlots) {
            if (complication.enabled) {
                complication.render(canvas, zonedDateTime, renderParameters)
            }
        }
    }

    override fun renderHighlightLayer(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: MatchmakingRatingSharedAssets
    ) {
        canvas.drawColor(renderParameters.highlightLayer!!.backgroundTint)

        for ((_, complication) in complicationSlotsManager.complicationSlots) {
            if (complication.enabled) {
                complication.renderHighlightLayer(canvas, zonedDateTime, renderParameters)
            }
        }
    }

    companion object {
        private const val DEFAULT_FRAME_UPDATE_DELAY_MS = 16L
        private const val TIME_FORMAT_AMBIENT = "HH:mm"
        private const val TIME_FORMAT_INTERACTIVE = "HH:mm:ss"
    }
}
