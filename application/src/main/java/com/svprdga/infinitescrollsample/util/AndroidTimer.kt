package com.svprdga.infinitescrollsample.util

import android.os.Handler
import com.svprdga.infinitescrollsample.util.abstraction.ITimer

/**
 * Class which uses internally an [android.os.Handler] to schedule tasks.
 */
class AndroidTimer : ITimer {

    // ****************************************** VARS ***************************************** //

    private val handler: Handler = Handler()

    // ************************************** CONSTRUCTORS ************************************* //

    override fun executeDelayed(timerCallback: ITimer.TimerCallback, delay: Long) {
        handler.postDelayed({ timerCallback.onExecute() }, delay)
    }

    override fun executeLoop(timerCallback: ITimer.TimerCallback, interval: Long) {
        val runnable = object : Runnable {
            override fun run() {
                timerCallback.onExecute()
                handler.postDelayed(this, interval)
            }
        }

        handler.post(runnable)
    }

    override fun cancel() {
        handler.removeCallbacksAndMessages(null)
    }
}
