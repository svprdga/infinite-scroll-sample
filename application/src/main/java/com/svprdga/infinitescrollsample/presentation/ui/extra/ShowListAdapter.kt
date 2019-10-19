package com.svprdga.infinitescrollsample.presentation.ui.extra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Show

class ShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // ***************************************** VIEWS ***************************************** //

    private val nameTextView: TextView =
        view.findViewById(R.id.name)
    private val overviewTextView: TextView =
        view.findViewById(R.id.overview)
    private val ratingTextView: TextView =
        view.findViewById(R.id.rating)

    // ************************************* PUBLIC METHODS ************************************ //

    fun initializeView(show: Show) {
        nameTextView.text = show.name
        overviewTextView.text = show.overview
        ratingTextView.text = show.averageRating.toString()
    }
}

class ShowListAdapter(
    private val shows: List<Show>)
    : RecyclerView.Adapter<ShowViewHolder>() {

    // ************************************* PUBLIC METHODS ************************************ //

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_show_entry, parent, false)
        return ShowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shows.size
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = shows[position]
        holder.initializeView(show)
    }


}