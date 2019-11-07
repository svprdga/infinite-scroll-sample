package com.svprdga.infinitescrollsample.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IListView
import com.svprdga.infinitescrollsample.presentation.ui.activity.DetailsActivity
import com.svprdga.infinitescrollsample.presentation.ui.activity.INTENT_SHOW
import com.svprdga.infinitescrollsample.presentation.ui.extra.EndlessRecyclerViewScrollListener
import com.svprdga.infinitescrollsample.presentation.ui.extra.ItemDecoration
import com.svprdga.infinitescrollsample.presentation.ui.extra.ShowListAdapter
import com.svprdga.infinitescrollsample.presentation.ui.extra.ShowListener
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.ext.android.inject

class ListFragment : Fragment(), IListView {

    // ************************************* INJECTED VARS ************************************* //

    val presenter: IListPresenter by inject()

    // ****************************************** VARS ***************************************** //

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var shows: MutableList<Show> = mutableListOf()
    private var showListAdapter: ShowListAdapter? = null

    private val showListener = object : ShowListener {
        override fun onClick(show: Show) {
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra(INTENT_SHOW, show)
            startActivity(intent)
        }
    }

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recylcer view set-up.
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                presenter.loadNextShowSet()
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
        recyclerView.addItemDecoration(ItemDecoration(context!!))

        presenter.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unBind()
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun appendShows(newShows: List<Show>) {

        shows.addAll(newShows)

        if (showListAdapter == null) {
            showListAdapter = ShowListAdapter(activity!!, shows, showListener)
            recyclerView.adapter = showListAdapter
        }

        recyclerView.apply {

            adapter?.notifyItemRangeChanged(0, shows.size - 1)
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