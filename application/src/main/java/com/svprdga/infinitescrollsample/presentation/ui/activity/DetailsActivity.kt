package com.svprdga.infinitescrollsample.presentation.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IDetailsPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IDetailsView
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import org.koin.android.ext.android.inject

const val INTENT_SHOW = "show"

class DetailsActivity : AppCompatActivity(), IDetailsView {

    // ************************************* INJECTED VARS ************************************* //

    val presenter: IDetailsPresenter by inject()

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Toolbar.
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get show.
        val show = intent.getSerializableExtra(INTENT_SHOW) as Show

        // Load preview image.
        show.imagePath?.let {
            Glide.with(this).load(show.imagePath).into(imageView)
        }

        // Set current favorite state.
        if (show.isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_white_24dp)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_border_white_24dp)
        }

        favoriteButton.setOnClickListener {
            presenter.favoriteButtonClick()
        }

        supportActionBar?.title = show.name

        presenter.show = show
        presenter.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unBind()
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun setFavoriteIcon() {
        favoriteButton.setImageResource(R.drawable.ic_favorite_white_24dp)
    }

    override fun setUncheckedFavoriteIcon() {
        favoriteButton.setImageResource(R.drawable.ic_favorite_border_white_24dp)
    }

    override fun showSmallPopup(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun startAnimations() {
        ratingIcon.doAnimation()
    }
}