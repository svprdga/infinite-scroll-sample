package com.svprdga.infinitescrollsample.presentation.ui.fragment

import androidx.fragment.app.Fragment
import androidx.test.espresso.IdlingResource

abstract class BaseFragment : Fragment(), IdlingResource {

    // ****************************************** VARS ***************************************** //

    var idle = false
        set(value) {
            if (field != value) {
                field = value
                if (value && idleCallback != null) {
                    idleCallback?.onTransitionToIdle()
                }
            }
        }
    private var idleCallback: IdlingResource.ResourceCallback? = null
    private var idleResources = mutableListOf<String>()

    // ************************************* PUBLIC METHODS ************************************ //

    override fun getName(): String {
        return javaClass.name
    }

    override fun isIdleNow(): Boolean {
        return idle && idleResources.isEmpty()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        idleCallback = callback
    }

    /**
     * Removes a resource from the idle resources list.
     */
    fun unregisterIdleResource(identifier: String) {
        idleResources.remove(identifier)
        updateIdle()
    }

    /**
     * Add a resource to the idle resources list.
     *
     * @param identifier
     */
    fun registerIdleResource(identifier: String) {
        idleResources.add(identifier)
        updateIdle()
    }

    // ************************************ PRIVATE METHODS ************************************ //

    /**
     * Updates the idle state.
     */
    private fun updateIdle() {
        idle = idleResources.isEmpty()
    }

}