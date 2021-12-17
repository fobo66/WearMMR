package io.github.fobo66.wearmmr.model

class MatchmakingWatchFaceViewModel {

    fun calculateNextTimeTick(): Long =
        INTERACTIVE_UPDATE_RATE_MS - System.currentTimeMillis() % INTERACTIVE_UPDATE_RATE_MS

    companion object {
        /**
         * Updates rate in milliseconds for interactive mode. We update once a second since seconds
         * are displayed in interactive mode.
         */
        const val INTERACTIVE_UPDATE_RATE_MS = 1000
    }
}
