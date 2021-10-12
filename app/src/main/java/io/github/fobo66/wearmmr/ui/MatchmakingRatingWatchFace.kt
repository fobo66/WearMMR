/*
 * Copyright 2018. Andrey Mukamolov <fobo66@protonmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.fobo66.wearmmr.ui

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationData.TYPE_SHORT_TEXT
import android.support.wearable.complications.SystemProviders
import android.support.wearable.complications.rendering.ComplicationDrawable
import android.support.wearable.watchface.CanvasWatchFaceService
import android.support.wearable.watchface.WatchFaceService
import android.support.wearable.watchface.WatchFaceStyle
import android.view.SurfaceHolder
import android.view.WindowInsets
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.github.fobo66.wearmmr.BATTERY_PROVIDER_ID
import io.github.fobo66.wearmmr.R
import io.github.fobo66.wearmmr.RATING_PROVIDER_ID
import io.github.fobo66.wearmmr.RatingComplicationProviderService
import io.github.fobo66.wearmmr.model.MatchmakingWatchFaceViewModel.Companion.INTERACTIVE_UPDATE_RATE_MS
import io.github.fobo66.wearmmr.util.GlideApp
import io.github.fobo66.wearmmr.util.TimeUpdateHandler
import io.github.fobo66.wearmmr.util.TimeUpdateHandler.Companion.MSG_UPDATE_TIME
import org.joda.time.DateTimeZone
import org.joda.time.LocalTime
import java.util.*

/**
 * Digital watch face with seconds. In ambient mode, the seconds aren't displayed. On devices with
 * low-bit ambient mode, the text is drawn without anti-aliasing in ambient mode.
 *
 *
 * Important Note: Because watch face apps do not have a default Activity in
 * their project, you will need to set your Configurations to
 * "Do not launch Activity" for both the Wear and/or Application modules. If you
 * are unsure how to do this, please review the "Run Starter project" section
 * in the Google Watch Face Code Lab:
 * https://codelabs.developers.google.com/codelabs/watchface/index.html#0
 */
class MatchmakingRatingWatchFace : CanvasWatchFaceService() {

    override fun onCreateEngine(): Engine {
        return Engine()
    }

    inner class Engine : CanvasWatchFaceService.Engine(true) {

        private lateinit var time: LocalTime

        private val ambientTimeFormat: String = getString(R.string.time_format_ambient)

        private val regularTimeFormat: String = getString(R.string.time_format)

        private var registeredTimeZoneReceiver = false

        private var timeXOffset: Float = 0F
        private var timeYOffset: Float = 0F

        private var complicationYOffset: Float = 30F

        private lateinit var backgroundPaint: Paint
        private lateinit var backgroundBitmap: Bitmap

        private lateinit var textPaint: Paint
        private lateinit var batteryComplication: ComplicationDrawable
        private lateinit var ratingComplication: ComplicationDrawable

        /**
         * Whether the display supports fewer bits for each color in ambient mode. When true, we
         * disable anti-aliasing in ambient mode.
         */
        private var lowBitAmbient: Boolean = false
        private var burnInProtection: Boolean = false
        private var modeAmbient: Boolean = false

        private val updateTimeHandler: Handler = TimeUpdateHandler(this)

        private val timeZoneReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                updateTimeZone()
                invalidate()
            }
        }

        private val timeFormat: String
            get() {
                return if (modeAmbient)
                    ambientTimeFormat
                else regularTimeFormat
            }

        override fun onCreate(holder: SurfaceHolder) {
            super.onCreate(holder)

            setWatchFaceStyle(
                WatchFaceStyle.Builder(this@MatchmakingRatingWatchFace)
                    .setAcceptsTapEvents(true)
                    .build()
            )

            setActiveComplications(BATTERY_PROVIDER_ID, RATING_PROVIDER_ID)
            setDefaultSystemComplicationProvider(
                BATTERY_PROVIDER_ID,
                SystemProviders.WATCH_BATTERY, TYPE_SHORT_TEXT
            )
            setDefaultComplicationProvider(
                RATING_PROVIDER_ID,
                ComponentName(applicationContext, RatingComplicationProviderService::class.java),
                TYPE_SHORT_TEXT
            )

            time = LocalTime.now()

            val resources = baseContext.resources
            timeYOffset = resources.getDimension(R.dimen.digital_y_offset)

            // Initializes background.
            backgroundPaint = Paint().apply {
                color = ContextCompat.getColor(applicationContext, R.color.background)
            }

            backgroundBitmap = (ResourcesCompat.getDrawable(
                resources,
                R.drawable.dota_logo,
                null
            ) as BitmapDrawable).bitmap

            // Initializes Watch Face.
            textPaint = Paint().apply {
                typeface =
                    ResourcesCompat.getFont(baseContext, R.font.trajan_pro)
                isAntiAlias = true
                color = ContextCompat.getColor(applicationContext, R.color.digital_text)
            }

            batteryComplication = ComplicationDrawable(applicationContext)
            ratingComplication = ResourcesCompat.getDrawable(
                resources,
                R.drawable.rating_complication_drawable,
                null
            ) as ComplicationDrawable
            ratingComplication.setContext(baseContext)
        }

        override fun onDestroy() {
            updateTimeHandler.removeMessages(MSG_UPDATE_TIME)
            super.onDestroy()
        }

        override fun onPropertiesChanged(properties: Bundle) {
            super.onPropertiesChanged(properties)
            lowBitAmbient = properties.getBoolean(
                WatchFaceService.PROPERTY_LOW_BIT_AMBIENT, false
            )
            burnInProtection = properties.getBoolean(
                WatchFaceService.PROPERTY_BURN_IN_PROTECTION, false
            )
        }

        override fun onTimeTick() {
            super.onTimeTick()
            invalidate()
        }

        override fun onAmbientModeChanged(inAmbientMode: Boolean) {
            super.onAmbientModeChanged(inAmbientMode)
            modeAmbient = inAmbientMode

            if (lowBitAmbient) {
                textPaint.isAntiAlias = !inAmbientMode
            }

            batteryComplication.setInAmbientMode(inAmbientMode)
            ratingComplication.setInAmbientMode(inAmbientMode)

            // Whether the timer should be running depends on whether we're visible (as well as
            // whether we're in ambient mode), so we may need to start or stop the timer.
            updateTimer()
        }

        override fun onComplicationDataUpdate(
            watchFaceComplicationId: Int,
            data: ComplicationData?
        ) {
            when (watchFaceComplicationId) {
                BATTERY_PROVIDER_ID -> batteryComplication.setComplicationData(data)
                RATING_PROVIDER_ID -> ratingComplication.setComplicationData(data)
            }
            invalidate()
        }

        override fun onTapCommand(tapType: Int, x: Int, y: Int, eventTime: Long) {
            when (tapType) {
                WatchFaceService.TAP_TYPE_TOUCH -> {
                    // The user has started touching the screen.
                }
                WatchFaceService.TAP_TYPE_TOUCH_CANCEL -> {
                    // The user has started a different gesture or otherwise cancelled the tap.
                }
                WatchFaceService.TAP_TYPE_TAP ->
                    // The user has completed the tap gesture.
                    MainActivity.start(baseContext)
            }
            invalidate()
        }

        override fun onSurfaceChanged(
            holder: SurfaceHolder?,
            format: Int,
            width: Int,
            height: Int
        ) {
            super.onSurfaceChanged(holder, format, width, height)

            val sizeOfComplication = width / 4
            val midpointOfScreen = width / 2

            val horizontalOffset = (midpointOfScreen - sizeOfComplication) / 2
            val verticalOffset = midpointOfScreen - (sizeOfComplication / 2) + complicationYOffset

            val batteryComplicationBounds = Rect(
                horizontalOffset,
                verticalOffset.toInt(),
                (horizontalOffset + sizeOfComplication),
                ((verticalOffset + sizeOfComplication).toInt())
            )

            batteryComplication.bounds = batteryComplicationBounds

            val ratingComplicationBounds = Rect(
                (midpointOfScreen + horizontalOffset),
                verticalOffset.toInt(),
                (midpointOfScreen + horizontalOffset + sizeOfComplication),
                ((verticalOffset + sizeOfComplication).toInt())
            )

            ratingComplication.bounds = ratingComplicationBounds
        }

        override fun onDraw(canvas: Canvas, bounds: Rect) {
            // Draw the background.
            if (modeAmbient) {
                canvas.drawColor(Color.BLACK)
            } else {
                GlideApp.with(baseContext)
                    .asBitmap()
                    .override(bounds.width(), bounds.height())
                    .fitCenter()
                    .load(R.drawable.dota_logo)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            canvas.drawBitmap(resource, 0f, 0f, backgroundPaint)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            canvas.drawPaint(backgroundPaint)
                        }
                    })
            }

            // Draw H:MM in ambient mode or H:MM:SS in interactive mode.
            val now = System.currentTimeMillis()
            time = LocalTime.now()

            canvas.drawText(time.toString(timeFormat), timeXOffset, timeYOffset, textPaint)

            batteryComplication.draw(canvas, now)
            ratingComplication.draw(canvas, now)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)

            if (visible) {
                registerTimeZoneReceiver()

                // Update time zone in case it changed while we weren't visible.
                updateTimeZone()
                invalidate()
            } else {
                unregisterTimeZoneReceiver()
            }

            // Whether the timer should be running depends on whether we're visible (as well as
            // whether we're in ambient mode), so we may need to start or stop the timer.
            updateTimer()
        }

        private fun updateTimeZone() {
            time = LocalTime(DateTimeZone.forTimeZone(TimeZone.getDefault()))
        }

        private fun registerTimeZoneReceiver() {
            if (registeredTimeZoneReceiver) {
                return
            }
            registeredTimeZoneReceiver = true
            val filter = IntentFilter(Intent.ACTION_TIMEZONE_CHANGED)
            LocalBroadcastManager.getInstance(baseContext)
                .registerReceiver(timeZoneReceiver, filter)
        }

        private fun unregisterTimeZoneReceiver() {
            if (!registeredTimeZoneReceiver) {
                return
            }
            registeredTimeZoneReceiver = false
            LocalBroadcastManager.getInstance(baseContext)
                .unregisterReceiver(timeZoneReceiver)
        }

        override fun onApplyWindowInsets(insets: WindowInsets) {
            super.onApplyWindowInsets(insets)

            // Load resources that have alternate values for round watches.
            val isRound = insets.isRound
            timeXOffset = resources.getDimension(
                if (isRound)
                    R.dimen.digital_x_offset_round
                else
                    R.dimen.digital_x_offset
            )

            complicationYOffset = resources.getDimension(
                if (isRound)
                    R.dimen.complication_y_offset_round
                else
                    R.dimen.complication_y_offset
            )

            val textSize = resources.getDimension(
                if (isRound)
                    R.dimen.digital_text_size_round
                else
                    R.dimen.digital_text_size
            )

            textPaint.textSize = textSize
        }

        /**
         * Starts the [.updateTimeHandler] timer if it should be running and isn't currently
         * or stops it if it shouldn't be running but currently is.
         */
        private fun updateTimer() {
            updateTimeHandler.removeMessages(MSG_UPDATE_TIME)
            if (shouldTimerBeRunning()) {
                updateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME)
            }
        }

        /**
         * Returns whether the [.updateTimeHandler] timer should be running. The timer should
         * only run when we're visible and in interactive mode.
         */
        private fun shouldTimerBeRunning(): Boolean {
            return isVisible && !isInAmbientMode
        }

        /**
         * Handle updating the time periodically in interactive mode.
         */
        fun handleUpdateTimeMessage() {
            invalidate()
            if (shouldTimerBeRunning()) {
                val timeMs = System.currentTimeMillis()
                val delayMs = INTERACTIVE_UPDATE_RATE_MS - timeMs % INTERACTIVE_UPDATE_RATE_MS
                updateTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, delayMs)
            }
        }
    }
}
