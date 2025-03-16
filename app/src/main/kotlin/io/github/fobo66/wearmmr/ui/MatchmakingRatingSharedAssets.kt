/*
 *    Copyright 2025 Andrey Mukamolov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.fobo66.wearmmr.ui

import android.content.Context
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.wear.watchface.Renderer
import io.github.fobo66.wearmmr.R

class MatchmakingRatingSharedAssets(context: Context) : Renderer.SharedAssets {
    val backgroundAmbientPaint =
        Paint().apply {
            color = ContextCompat.getColor(context, R.color.background_ambient)
        }

    val backgroundPaint =
        Paint().apply {
            color = ContextCompat.getColor(context, R.color.background)
        }

    val timeXOffset =
        context.resources.getDimension(
            R.dimen.digital_x_offset
        )

    val timeYOffset = context.resources.getDimension(R.dimen.digital_y_offset)

    val textPaint =
        Paint().apply {
            typeface =
                ResourcesCompat.getFont(context, R.font.cinzel)
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.digital_text)
            textSize =
                context.resources.getDimension(
                    R.dimen.digital_text_size
                )
        }

    override fun onDestroy() {
        backgroundPaint.reset()
        textPaint.reset()
    }
}
