package com.svprdga.infinitescrollsample.presentation.ui.extra

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IShowPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IShowView
import org.koin.core.KoinComponent
import org.koin.core.inject

private const val CARD_CORNER_RADIUS = 20

interface ShowListener {
    /**
     * The user clicked on the Show entry.
     */
    fun onClick(show: Show)
}

class ShowView(private val context: Context, view: View) : IShowView, RecyclerView.ViewHolder(view),
    KoinComponent {

    // ************************************* INJECTED VARS ************************************* //

    val presenter: IShowPresenter by inject()

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

    fun initializeView(show: Show, listener: ShowListener) {
        presenter.show = show
        presenter.bind(this)

        cardView.radius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            CARD_CORNER_RADIUS.toFloat(),
            context.resources.displayMetrics
        )

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
            presenter.favoriteButtonClick(layoutPosition)
        }

        cardView.setOnClickListener {
            listener.onClick(show)
        }
    }

    override fun showSmallPopup(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun setFavoriteIcon() {
        favoriteButton.setImageResource(R.drawable.ic_favorite_black_36dp)
    }

    override fun setUncheckedFavoriteIcon() {
        favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_36dp)
    }
}