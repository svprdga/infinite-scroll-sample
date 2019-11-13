package com.svprdga.infinitescrollsample.presentation.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

class CustomScrollView(context: Context, attrs: AttributeSet) : NestedScrollView(context, attrs) {

    // ****************************************** VARS ***************************************** //

    var isScrollable = true

    // ************************************* PUBLIC METHODS ************************************ //

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return when(ev.action) {
            MotionEvent.ACTION_DOWN -> isScrollable && super.onTouchEvent(ev)
            else -> super.onTouchEvent(ev)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return isScrollable && super.onInterceptTouchEvent(ev)
    }

}