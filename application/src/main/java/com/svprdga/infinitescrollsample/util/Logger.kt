package com.svprdga.infinitescrollsample.util

import android.util.Log
import com.svprdga.infinitescrollsample.domain.Mockable

/**
 * Main log class.
 */
@Mockable
class Logger(private val tag: String) {

    // ************************************* PUBLIC METHODS ************************************ //

    /**
     * Log a fatal error. <br></br>
     * A fatal error is something that will have user-visible consequences
     * and won't be recoverable without explicitly deleting some data, uninstalling applications,
     * wiping the data partitions or reflashing the entire device (or worse).
     * @param log Error message to log.
     */
    fun fatal(log: String) {
        Log.e(tag, log)
    }

    /**
     * Log a non-fatal error. <br></br>
     * A non fatal error is something that will have user-visible consequences but is likely to
     * be recoverable without data loss by performing some explicit action,
     * ranging from waiting or restarting an app all the way to re-downloading a new version
     * of an application or rebooting the device.
     * @param log Error message to log.
     */
    fun error(log: String) {
        Log.w(tag, log)
    }

    /**
     * Log an info message. <br></br>
     * Use to note that something interesting to most people happened, i.e. when a situation
     * is detected that is likely to have widespread impact, though isn't necessarily an error.
     * @param log Info message to log.
     */
    fun info(log: String) {
        Log.i(tag, log)
    }

    /**
     * Log a debug message. <br></br>
     * Use to further note what is happening on the device that could be relevant to
     * investigate and debug unexpected behaviors.
     * @param log Debug message to log.
     */
    fun debug(log: String) {
        Log.d(tag, log)
    }
}
