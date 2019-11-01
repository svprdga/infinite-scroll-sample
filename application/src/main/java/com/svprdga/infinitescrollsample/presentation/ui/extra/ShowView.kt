package com.svprdga.infinitescrollsample.presentation.ui.extra

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.svprdga.infinitescrollsample.R
//import com.svprdga.infinitescrollsample.di.component.AppComponent
//import com.svprdga.infinitescrollsample.di.component.UiComponent
//import com.svprdga.infinitescrollsample.di.module.PresenterModule
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IShowPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IShowView
import com.svprdga.infinitescrollsample.presentation.ui.application.CoreApp
//import javax.inject.Inject

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

class ShowView(view: View) : RecyclerView.ViewHolder(view) {

    // ***************************************** VIEWS ***************************************** //

    private val cardView: CardView =
        view.findViewById(R.id.cardView)
    private val nameTextView: TextView =
        view.findViewById(R.id.name)
    private val overviewTextView: TextView =
        view.findViewById(R.id.overview)
    private val ratingTextView: TextView =
        view.findViewById(R.id.ratingValue)
    private val imageView: ImageView =
        view.findViewById(R.id.thumbnail)
    private val ratingBar: RatingBar =
        view.findViewById(R.id.ratingBar)
    private val favoriteButton: ImageButton =
        view.findViewById(R.id.favoriteButton)

    // ************************************* PUBLIC METHODS ************************************ //

    fun initializeView(show: Show, context: Context, listener: ShowListener) {

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

        if (show.isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_black_36dp)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_36dp)
        }

        favoriteButton.setOnClickListener {

        }
    }
}