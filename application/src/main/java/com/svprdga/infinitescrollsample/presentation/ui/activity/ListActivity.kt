package com.svprdga.infinitescrollsample.presentation.ui.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IListView
import com.svprdga.infinitescrollsample.presentation.ui.extra.EndlessRecyclerViewScrollListener
import com.svprdga.infinitescrollsample.presentation.ui.extra.ShowListAdapter
import com.svprdga.infinitescrollsample.util.Logger
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.svprdga.infinitescrollsample.presentation.ui.extra.ItemDecoration

class ListActivity : BaseActivity(), IListView {

    // ************************************* INJECTED VARS ************************************* //

    @Inject
    lateinit var log: Logger
    @Inject
    lateinit var presenter: IListPresenter

    // ****************************************** VARS ***************************************** //

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var shows: MutableList<Show> = mutableListOf()

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        uiComponent?.inject(this)
        setContentView(R.layout.activity_main)

        // Toolbar.
        setSupportActionBar(toolbar)

        // Recylcer view set-up.
        val layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        recyclerView.layoutManager = layoutManager
        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                presenter.loadNextShowSet()
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
        recyclerView.addItemDecoration(ItemDecoration(this))

        presenter.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unBind()
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun appendShows(newShows: List<Show>) {
        shows.addAll(newShows)
        val context = this
        recyclerView.apply {
            adapter = ShowListAdapter(context, shows)
            adapter?.notifyItemRangeChanged(0, shows.size)
        }
    }

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
