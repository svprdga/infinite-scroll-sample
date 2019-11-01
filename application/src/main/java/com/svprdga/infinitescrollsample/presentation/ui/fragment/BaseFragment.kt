package com.svprdga.infinitescrollsample.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.svprdga.infinitescrollsample.presentation.ui.activity.BaseActivity

abstract class BaseFragment : Fragment() {

    // ****************************************** VARS ***************************************** //

    protected val baseActivity
        get() = activity as BaseActivity
//    private val coreApp: CoreApp
//        get() = baseActivity.application as CoreApp
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        appComponent.inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
//        uiComponent = null
    }

}