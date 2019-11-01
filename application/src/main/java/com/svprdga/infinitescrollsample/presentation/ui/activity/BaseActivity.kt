package com.svprdga.infinitescrollsample.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    // ****************************************** VARS ***************************************** //

//    private val coreApp: CoreApp
//        get() = application as CoreApp
//    private val appComponent: AppComponent
//        get() = coreApp.appComponent
//    protected var uiComponent: UiComponent? = null
//        get() {
//            if (field == null) {
//                field = appComponent.plusUiComponent(
//                    PresenterModule()
//                )
//            }
//            return field
//        }

    // *************************************** LIFECYCLE *************************************** //

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        appComponent.inject(this)
    }

    public override fun onDestroy() {
        super.onDestroy()
//        uiComponent = null
    }

}
