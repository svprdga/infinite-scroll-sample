package com.svprdga.infinitescrollsample.presentation.ui.activity

import android.os.Bundle
import android.view.View
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IListView
import com.svprdga.infinitescrollsample.util.Logger
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class ListActivity : BaseActivity(), IListView {

    // ************************************* INJECTED VARS ************************************* //

    @Inject
    lateinit var log: Logger
    @Inject
    lateinit var presenter: IListPresenter

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        uiComponent?.inject(this)
        setContentView(R.layout.activity_main)

        // Toolbar.
        setSupportActionBar(toolbar)

        presenter.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unBind()
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun hideListLayout() {
        listLayout.visibility = View.GONE
    }

    override fun showErrorLayout() {
        errorLayout.visibility = View.VISIBLE
    }

    override fun hideErrorLayout() {
        errorLayout.visibility = View.GONE
    }
}
