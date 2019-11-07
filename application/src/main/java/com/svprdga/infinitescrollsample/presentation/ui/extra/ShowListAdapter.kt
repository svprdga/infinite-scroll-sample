package com.svprdga.infinitescrollsample.presentation.ui.extra

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Show

class ShowListAdapter(
    private val context: Context,
    private val shows: MutableList<Show>,
    private val showListener: ShowListener
) : RecyclerView.Adapter<ShowView>() {

    // ************************************* PUBLIC METHODS ************************************ //

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowView {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_show_entry, parent, false)
        return ShowView(context, view)
    }

    override fun getItemCount(): Int {

        return shows.size
    }

    override fun onBindViewHolder(holder: ShowView, position: Int) {
        val show = shows[position]
        holder.initializeView(show, showListener)
    }

    fun clear() {
        shows.clear()
    }
}