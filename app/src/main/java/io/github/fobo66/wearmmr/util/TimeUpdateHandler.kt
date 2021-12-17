package io.github.fobo66.wearmmr.util

import android.os.Handler
import android.os.Looper
import android.os.Message
import io.github.fobo66.wearmmr.ui.MatchmakingRatingWatchFace
import java.lang.ref.WeakReference

class TimeUpdateHandler(reference: MatchmakingRatingWatchFace.Engine) :
    Handler(Looper.getMainLooper()) {
    private val engineReference: WeakReference<MatchmakingRatingWatchFace.Engine> = WeakReference(
        reference
    )

    override fun handleMessage(msg: Message) {
        when (msg.what) {
            MSG_UPDATE_TIME -> engineReference.get()?.handleUpdateTimeMessage()
        }
    }

    companion object {
        /**
         * Handler message id for updating the time periodically in interactive mode.
         */
        const val MSG_UPDATE_TIME = 123
    }
}
