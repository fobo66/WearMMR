package io.github.fobo66.wearmmr.ui

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.wear.watchface.Renderer
import io.github.fobo66.wearmmr.R

class MatchmakingRatingSharedAssets(context: Context) : Renderer.SharedAssets {

    val backgroundPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.background)
    }

    val backgroundBitmap = (
        ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.dota_logo,
            null
        ) as BitmapDrawable
        ).bitmap

    val timeXOffset = context.resources.getDimension(
        R.dimen.digital_x_offset
    )

    val timeYOffset = context.resources.getDimension(R.dimen.digital_y_offset)

    val complicationYOffset = context.resources.getDimension(
        R.dimen.complication_y_offset
    )

    val textPaint = Paint().apply {
        typeface =
            ResourcesCompat.getFont(context, R.font.trajan_pro)
        isAntiAlias = true
        color = ContextCompat.getColor(context, R.color.digital_text)
        textSize = context.resources.getDimension(
            R.dimen.digital_text_size
        )
    }

    override fun onDestroy() {
        backgroundPaint.reset()
        textPaint.reset()
        backgroundBitmap.recycle()
    }
}
