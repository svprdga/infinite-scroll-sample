package com.svprdga.infinitescrollsample.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IListView
import com.svprdga.infinitescrollsample.presentation.ui.extra.EndlessRecyclerViewScrollListener
import com.svprdga.infinitescrollsample.presentation.ui.extra.ItemDecoration
import com.svprdga.infinitescrollsample.presentation.ui.extra.ShowListAdapter
import com.svprdga.infinitescrollsample.presentation.ui.extra.ShowListener
import com.svprdga.infinitescrollsample.util.Logger
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class ListFragment : BaseFragment(), IListView {
    // ************************************* INJECTED VARS ************************************* //

    @Inject
    lateinit var log: Logger
    @Inject
    lateinit var presenter: IListPresenter

    // ****************************************** VARS ***************************************** //

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var shows: MutableList<Show> = mutableListOf()
    private var showListAdapter: ShowListAdapter? = null

    private var showListener = object : ShowListener {
        override fun onUndoFavorite(show: Show, position: Int) {
//            viewPosition = position
//            presenter.makeShowNotFavorite(show)
        }

        override fun onMakeFavorite(show: Show) {
            // Cannot happen on this context.
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

        uiComponent?.inject(this)

        // Recylcer view set-up.
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                presenter.loadNextShowSet()
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
        recyclerView.addItemDecoration(ItemDecoration(baseActivity))

        presenter.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unBind()
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun hideAll() {
        mainContainer.visibility = View.INVISIBLE
    }

    override fun showAll() {
        mainContainer.visibility = View.VISIBLE
    }

    override fun appendShows(newShows: List<Show>) {

        shows.addAll(newShows)

        if (showListAdapter == null) {
            showListAdapter = ShowListAdapter(baseActivity, shows, showListener)
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