package com.svprdga.infinitescrollsample.uitest.custom

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.svprdga.infinitescrollsample.uitest.application.TestApp

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}