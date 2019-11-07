package com.svprdga.infinitescrollsample.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IFavoritesPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IFavoritesView
import com.svprdga.infinitescrollsample.presentation.ui.activity.DetailsActivity
import com.svprdga.infinitescrollsample.presentation.ui.activity.INTENT_SHOW
import com.svprdga.infinitescrollsample.presentation.ui.extra.ShowListAdapter
import com.svprdga.infinitescrollsample.presentation.ui.extra.ShowListener
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_list.recyclerView
import org.koin.android.ext.android.inject

class FavoritesFragment : Fragment(), IFavoritesView {

    // ************************************* INJECTED VARS ************************************* //

    val presenter: IFavoritesPresenter by inject()

    // ****************************************** VARS ***************************************** //

    private var shows: MutableList<Show> = mutableListOf()
    private var adapter: ShowListAdapter? = null

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
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun setFavorites(shows: List<Show>) {
        this.shows = shows.toMutableList()

        adapter =
            ShowListAdapter(activity!!, this.shows, showListener)
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

    override fun removeShowFromList(position: Int) {
        shows.removeAt(position)
        adapter?.notifyItemRemoved(position)
    }
}