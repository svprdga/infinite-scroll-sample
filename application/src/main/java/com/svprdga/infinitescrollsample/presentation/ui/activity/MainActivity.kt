package com.svprdga.infinitescrollsample.presentation.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.svprdga.infinitescrollsample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar.
        setSupportActionBar(toolbar)

        // Configure tabs.
        val navController = findNavController(R.id.navController)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_list, R.id.navigation_favorites
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        // Create about dialog.
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.appName)
        builder.setMessage(R.string.main_aboutDesc)
        builder.setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss()}
        dialog = builder.create()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.about) {
            dialog.show()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}