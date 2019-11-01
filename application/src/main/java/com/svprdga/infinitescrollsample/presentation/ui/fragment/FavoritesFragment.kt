package com.svprdga.infinitescrollsample.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IFavoritesPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IFavoritesView
import com.svprdga.infinitescrollsample.presentation.ui.extra.ShowListAdapter
import com.svprdga.infinitescrollsample.presentation.ui.extra.ShowListener
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_list.recyclerView
import javax.inject.Inject

class FavoritesFragment : BaseFragment(), IFavoritesView {

    // ************************************* INJECTED VARS ************************************* //

    @Inject
    lateinit var presenter: IFavoritesPresenter

    // ****************************************** VARS ***************************************** //

    private var viewPosition: Int? = null
    private var shows: MutableList<Show> = mutableListOf()
    private var adapter: ShowListAdapter? = null

    private var showListener = object : ShowListener {
        override fun onUndoFavorite(show: Show, position: Int) {
            viewPosition = position
            presenter.makeShowNotFavorite(show)
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
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiComponent?.inject(this)

        // Recycler view set-up.
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        presenter.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unBind()
    }

    override fun showSmallPopup(message: String) {
        Toast.makeText(baseActivity, message, Toast.LENGTH_LONG).show()
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun hideAll() {
        mainContainer.visibility = View.INVISIBLE
    }

    override fun showAll() {
        mainContainer.visibility = View.VISIBLE
    }

    override fun setFavorites(shows: List<Show>) {
        this.shows = shows.toMutableList()

        adapter =
            ShowListAdapter(baseActivity, this.shows, showListener)
        recyclerView.adapter = adapter
    }

    override fun hideFavoritesList() {
        recyclerView.visibility = View.GONE
    }

    override fun showEmptyFavoritesLayout() {
        noFavoritesLayout.visibility = View.VISIBLE
    }

    override fun hideEmptyFavoritesLayout() {
        noFavoritesLayout.visibility = View.GONE
    }

    override fun removeShowFromList() {
        viewPosition?.let {
            shows.removeAt(it)
            adapter?.notifyItemRemoved(it)
        }
    }
}