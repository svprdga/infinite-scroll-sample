package com.svprdga.infinitescrollsample.uitest.test

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
abstract class BaseUiTest {

    /**
     * Suspend the current thread by the amount of [time] given.
     */
    protected fun waitFor(time: Long) {
        Thread.sleep(time)
    }

}