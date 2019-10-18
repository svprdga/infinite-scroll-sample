package com.svprdga.infinitescrollsample.presentation.ui.activity

import android.os.Bundle
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IListView
import com.svprdga.infinitescrollsample.util.Logger
import javax.inject.Inject

class ListActivity : BaseActivity(), IListView {

    // ************************************* INJECTED VARS ************************************* //

    @Inject
    lateinit var log: Logger
    @Inject
    lateinit var presenter: IListPresenter

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiComponent?.inject(this)
        setContentView(R.layout.activity_main)

        presenter.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unBind()
    }
}
