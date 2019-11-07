package com.svprdga.infinitescrollsample.util.abstraction

/**
 * Abstraction for a timer object to schedule tasks, execute them repeatedly, etc.
 */
interface ITimer {

    /**
     * Callback object for this [ITimer].
     */
    interface TimerCallback {
        /**
         * When the task is executed.
         */
        fun onExecute()
    }

    /**
     * Execute the given [TimerCallback] after the given `delay`.
     * @param timerCallback [TimerCallback] to execute after the delay.
     * @param delay Amount of time to wait until the `runnable` must be executed.
     */
    fun executeDelayed(timerCallback: TimerCallback, delay: Long)

    /**
     * Execute the given [TimerCallback] in a loop.
     * @param timerCallback [TimerCallback] to execute in each loop.
     * @param interval Amount of time between loops.
     */
    fun executeLoop(timerCallback: TimerCallback, interval: Long)

    /**
     * Cancels any pending tasks of this timer.
     */
    fun cancel()
}