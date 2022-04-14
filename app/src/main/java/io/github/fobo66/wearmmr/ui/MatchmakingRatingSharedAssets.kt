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

    override fun onDestroy() {
        backgroundPaint.reset()
        backgroundBitmap.recycle()
    }
}
