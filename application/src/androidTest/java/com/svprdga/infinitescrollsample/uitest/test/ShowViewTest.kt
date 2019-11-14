package com.svprdga.infinitescrollsample.uitest.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IShowPresenter
import com.svprdga.infinitescrollsample.presentation.ui.fragment.ListFragment
import com.svprdga.infinitescrollsample.uitest.custom.createRule
import com.svprdga.infinitescrollsample.uitest.stub.TestListPresenter
import com.svprdga.infinitescrollsample.uitest.stub.TestShowPresenter
import de.bechte.junit.runners.context.HierarchicalContextRunner
import org.hamcrest.Matchers.containsString
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module

@RunWith(HierarchicalContextRunner::class)
class ShowViewTest : BaseUiTest() {

    // ****************************************** VARS ***************************************** //

    private val listPresenter = TestListPresenter()
    private val presenter = TestShowPresenter()
    private val fragment = ListFragment()

    @get:Rule
    val fragmentRule = createRule(fragment, module {
        factory<IListPresenter>(override = true) {
            listPresenter
        }
        factory<IShowPresenter>(override = true) {
            presenter
        }
    })

    val show = Show(
        26, "Fake show", "Fake overview",
        42.3F, "fake image path", false
    )

    // ***************************************** SET UP **************************************** //

    @Before
    fun setUp() {
        fragmentRule.launch()

        fragmentRule.activity.runOnUiThread {
            listPresenter.view?.appendShows(mutableListOf(show))
        }

        waitFor(500)
    }

    @After
    fun after() {
        listPresenter.reset()
        presenter.reset()
    }

    // ***************************************** TESTS ***************************************** //

    inner class WhenCreatingView {

        @Test
        fun shouldSetName() {
            onView(withText(show.name))
                .check(matches(isDisplayed()))
        }

        @Test
        fun shouldSetOverview() {
            onView(withText(show.overview))
                .check(matches(isDisplayed()))
        }

        @Test
        fun shouldShowRatingBar() {
            onView(withId(R.id.ratingBar))
                .check(matches(isDisplayed()))
        }

        @Test
        fun shouldSetRating() {
            onView(withText(containsString(show.averageRating.toString())))
                .check(matches(isDisplayed()))
        }

    }

    inner class WhenClickInFavoriteButton {

        @Test
        fun shouldCallFavoriteButtonClickInPresenter() {
            onView(withId(R.id.favoriteButton))
                .perform(click())
            assertEquals(1, presenter.favoriteButtonClickCalled)
        }

    }

}