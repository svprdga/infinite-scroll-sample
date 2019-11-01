package com.svprdga.infinitescrollsample.presentation.ui.extra

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Show

private const val CARD_CORNER_RADIUS = 20

interface ShowListener{
    /**
     * The user selected the given [show] as favorite.
     */
    fun onMakeFavorite(show: Show)

    /**
     * The user choosed to undo the favorite category for the given [show].
     */
    fun onUndoFavorite(show: Show, position: Int)
}

class ShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // ***************************************** VIEWS ***************************************** //

    private val cardView: CardView =
        view.findViewById(R.id.cardView)
    private val nameTextView: TextView =
        view.findViewById(R.id.name)
    private val overviewTextView: TextView =
        view.findViewById(R.id.overview)
    private val ratingTextView: TextView =
        view.findViewById(R.id.rating)
    private val imageView: ImageView =
        view.findViewById(R.id.thumbnail)
    private val ratingBar: RatingBar =
        view.findViewById(R.id.ratingBar)

    // ************************************* PUBLIC METHODS ************************************ //

    fun initializeView(show: Show, context: Context, showListener: ShowListener) {
        cardView.radius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            CARD_CORNER_RADIUS.toFloat(),
            context.resources.displayMetrics)

        nameTextView.text = show.name
        overviewTextView.text = show.overview
        ratingTextView.text = " ${show.averageRating}"
        ratingBar.rating = show.averageRating / 2f

        show.imagePath?.let {
            Glide.with(context).load(show.imagePath).into(imageView)
        }
    }
}

class ShowListAdapter(
    private val context: Context,
    private val shows: List<Show>,
    private val showListener: ShowListener
) : RecyclerView.Adapter<ShowViewHolder>() {

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
        holder.initializeView(show, context, showListener)
    }
}