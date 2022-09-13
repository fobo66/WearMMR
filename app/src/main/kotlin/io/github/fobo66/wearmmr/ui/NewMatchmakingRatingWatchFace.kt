package io.github.fobo66.wearmmr.ui

import android.content.ComponentName
import android.graphics.RectF
import android.view.SurfaceHolder
import androidx.wear.watchface.CanvasComplicationFactory
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.ComplicationSlot
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.complications.ComplicationSlotBounds
import androidx.wear.watchface.complications.DefaultComplicationDataSourcePolicy
import androidx.wear.watchface.complications.SystemDataSources
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.rendering.CanvasComplicationDrawable
import androidx.wear.watchface.complications.rendering.ComplicationDrawable
import androidx.wear.watchface.style.CurrentUserStyleRepository
import io.github.fobo66.wearmmr.R
import io.github.fobo66.wearmmr.domain.RatingComplicationDataSource

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

    override fun createComplicationSlotsManager(
        currentUserStyleRepository: CurrentUserStyleRepository
    ): ComplicationSlotsManager {
        val complicationFactory = CanvasComplicationFactory { watchState, invalidateCallback ->
            CanvasComplicationDrawable(
                ComplicationDrawable.getDrawable(
                    applicationContext,
                    R.drawable.rating_complication_drawable
                )!!,
                watchState,
                invalidateCallback
            )
        }

        val leftComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
            id = ComplicationsConfig.Left.id,
            canvasComplicationFactory = complicationFactory,
            supportedTypes = ComplicationsConfig.Left.supportedTypes,
            defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
                ComponentName(applicationContext, RatingComplicationDataSource::class.java),
                ComplicationType.SHORT_TEXT,
                SystemDataSources.DATA_SOURCE_STEP_COUNT,
                ComplicationType.SHORT_TEXT
            ),
            bounds = ComplicationSlotBounds(
                RectF(
                    LEFT_COMPLICATION_LEFT_BOUND,
                    LEFT_AND_RIGHT_COMPLICATIONS_TOP_BOUND,
                    LEFT_COMPLICATION_RIGHT_BOUND,
                    LEFT_AND_RIGHT_COMPLICATIONS_BOTTOM_BOUND
                )
            )
        )
            .build()

        val rightComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
            id = ComplicationsConfig.Right.id,
            canvasComplicationFactory = complicationFactory,
            supportedTypes = ComplicationsConfig.Right.supportedTypes,
            defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
                SystemDataSources.DATA_SOURCE_WATCH_BATTERY,
                ComplicationType.SHORT_TEXT
            ),
            bounds = ComplicationSlotBounds(
                RectF(
                    RIGHT_COMPLICATION_LEFT_BOUND,
                    LEFT_AND_RIGHT_COMPLICATIONS_TOP_BOUND,
                    RIGHT_COMPLICATION_RIGHT_BOUND,
                    LEFT_AND_RIGHT_COMPLICATIONS_BOTTOM_BOUND
                )
            )
        )
            .build()

        return ComplicationSlotsManager(
            listOf(leftComplication, rightComplication),
            currentUserStyleRepository
        )
    }

    companion object {
        // Information needed for complications.
        // Creates bounds for the locations of both right and left complications. (This is the
        // location from 0.0 - 1.0.)
        // Both left and right complications use the same top and bottom bounds.
        private const val LEFT_AND_RIGHT_COMPLICATIONS_TOP_BOUND = 0.55f
        private const val LEFT_AND_RIGHT_COMPLICATIONS_BOTTOM_BOUND = 0.75f

        private const val LEFT_COMPLICATION_LEFT_BOUND = 0.2f
        private const val LEFT_COMPLICATION_RIGHT_BOUND = 0.4f

        private const val RIGHT_COMPLICATION_LEFT_BOUND = 0.6f
        private const val RIGHT_COMPLICATION_RIGHT_BOUND = 0.8f
    }
}
