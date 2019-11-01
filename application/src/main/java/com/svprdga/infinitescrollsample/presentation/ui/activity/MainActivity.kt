package com.svprdga.infinitescrollsample.presentation.ui.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.presentation.eventbus.AppFragment
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IMainPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.view.IMainView
import com.svprdga.infinitescrollsample.presentation.ui.fragment.FavoritesFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

enum class Tab {
    LIST,
    FAVORITES
}

class MainActivity : AppCompatActivity(), IMainView {

    // ************************************* INJECTED VARS ************************************* //

    val presenter: IMainPresenter by inject()

    // ****************************************** VARS ***************************************** //

    private var currentTab = Tab.LIST
    private var ignoreNextQuery = false
    private val handler = Handler()

    private val navigationListener = BottomNavigationView.OnNavigationItemSelectedListener {

        if (it.itemId == R.id.navigation_list) {
            currentTab = Tab.LIST
            supportActionBar?.title = getString(R.string.list_title)

            invalidateOptionsMenu()
            handler.post {
                ignoreNextQuery = false
            }
            presenter.onChangeFragment(AppFragment.LIST)
        } else {
            currentTab = Tab.FAVORITES
            supportActionBar?.title = getString(R.string.favorites_title)
            ignoreNextQuery = true
            invalidateOptionsMenu()
            presenter.onChangeFragment(AppFragment.FAVORITES)
        }

        true
    }

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar.
        setSupportActionBar(toolbar)

        // Configure tabs.
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        navView.setOnNavigationItemSelectedListener(navigationListener)

        val favoritesFragment = FavoritesFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.navController, favoritesFragment, favoritesFragment.javaClass.simpleName)
            .commit()

        presenter.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unBind()
    }
}