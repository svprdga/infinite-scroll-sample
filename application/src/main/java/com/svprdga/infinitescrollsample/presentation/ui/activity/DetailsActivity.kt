package com.svprdga.infinitescrollsample.presentation.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Show
import kotlinx.android.synthetic.main.activity_main.*

const val INTENT_SHOW = "show"

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Toolbar.
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get show.
        val show = intent.getSerializableExtra(INTENT_SHOW) as Show

        supportActionBar?.title = show.name
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}