package com.svprdga.infinitescrollsample.uitest.test

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IFavoritesPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IShowPresenter
import com.svprdga.infinitescrollsample.presentation.ui.extra.ShowView
import com.svprdga.infinitescrollsample.presentation.ui.fragment.FavoritesFragment
import com.svprdga.infinitescrollsample.uitest.custom.createRule
import com.svprdga.infinitescrollsample.uitest.stub.TestFavoritesPresenter
import com.svprdga.infinitescrollsample.uitest.stub.TestShowPresenter
import de.bechte.junit.runners.context.HierarchicalContextRunner
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import org.junit.*
import org.junit.runner.RunWith
import org.koin.dsl.module

private const val SHOW_NUMBER = 10

@RunWith(HierarchicalContextRunner::class)
class FavoritesFragmentTest : BaseUiTest() {

    // ****************************************** VARS ***************************************** //

    private val presenter = TestFavoritesPresenter()
    private val fragment = FavoritesFragment()

    @get:Rule
    val fragmentRule = createRule(fragment, module {
        factory<IFavoritesPresenter>(override = true) {
            presenter
        }
        factory<IShowPresenter>(override = true) {
            TestShowPresenter()
        }
    })

    // ***************************************** SET UP **************************************** //

    @After
    fun after() {
        presenter.reset()
    }

    // ***************************************** TESTS ***************************************** //

    inner class WhenViewIsCreated {

        @Before
        fun setUp() {
            fragmentRule.launch()
        }

        @Test
        fun shouldBoundToPresenter() {
            Assert.assertEquals(1, presenter.bindCalled)
        }

        @Test
        fun shouldCallOnStartInPresenter() {
            Assert.assertEquals(1, presenter.onStartCalled)
        }
    }

    inner class WhenCallingSetFavorites {

        @Before
        fun setUp() {
            fragmentRule.launch()

            fragmentRule.activity.runOnUiThread {
                presenter.view?.setFavorites(getTestShows())
            }

            waitFor(500)
        }

        @Test
        fun shouldLoadListOfShow() {
            Espresso.onView(ViewMatchers.withText("name_0"))
                .check(matches(isDisplayed()))
        }

        inner class WhenScrollingLayout {

            @Test
            fun shouldScrollCorrectly() {
                Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                    .perform(
                        RecyclerViewActions.scrollTo<ShowView>
                            (ViewMatchers.hasDescendant(ViewMatchers.withText("name_5")))
                    )
                    .check(matches(isDisplayed()))

            }
        }

    }

    inner class WhenCallingHideFavoritesList {

        @Test
        fun shouldHideListLayout() {
            fragmentRule.launch()
            fragmentRule.activity.runOnUiThread {
                presenter.view?.hideFavoritesList()
            }
            waitFor(500)

            Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                .check(matches(not(isDisplayed())))
        }

    }

    inner class WhenCallingShowEmptyFavoritesLayout {

        @Before
        fun setUp() {
            fragmentRule.launch()
            fragmentRule.activity.runOnUiThread {
                presenter.view?.hideFavoritesList()
                presenter.view?.showEmptyFavoritesLayout()
            }
            waitFor(500)
        }

        @Test
        fun shouldShowErrorLayout() {
            Espresso.onView(ViewMatchers.withId(R.id.noFavoritesImage))
                .check(matches(isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.noFavoritesTitle))
                .check(matches(isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.noFavoritesMessage))
                .check(matches(isDisplayed()))
        }

    }

    /**
     * The empty favorites layout is hidden by default. So in order to test this method we are
     * going first to show it, and then hide it. Then we can make assertions.
     */
    inner class WhenCallingHideEmptyFavoritesLayout {

        @Before
        fun setUp() {
            fragmentRule.launch()
            fragmentRule.activity.runOnUiThread {
                presenter.view?.hideFavoritesList()
                presenter.view?.showEmptyFavoritesLayout()
                presenter.view?.hideEmptyFavoritesLayout()
            }
            waitFor(500)
        }

        @Test
        fun shouldShowErrorLayout() {
            Espresso.onView(ViewMatchers.withId(R.id.noFavoritesImage))
                .check(matches(not(isDisplayed())))
            Espresso.onView(ViewMatchers.withId(R.id.noFavoritesTitle))
                .check(matches(not(isDisplayed())))
            Espresso.onView(ViewMatchers.withId(R.id.noFavoritesMessage))
                .check(matches(not(isDisplayed())))
        }

    }

    inner class WhenCallingRemoveShowFromList {

        @Before
        fun setUp() {
            fragmentRule.launch()
            fragmentRule.activity.runOnUiThread {
                presenter.view?.setFavorites(getTestShows())
                presenter.view?.removeShowFromList(0)
            }
            waitFor(500)
        }

        @Test
        fun showShouldBeRemoved() {
            Espresso.onView(ViewMatchers.withText("name_0"))
                .check(doesNotExist())
        }

    }

    /**
     * In order to test this clear method first we are going to populate the recycler view
     * with items and then clear them.
     */
    inner class WhenCallingClearList {

        @Before
        fun setUp() {
            fragmentRule.launch()
            fragmentRule.activity.runOnUiThread {
                presenter.view?.setFavorites(getTestShows())
                presenter.view?.clearList()
            }
            waitFor(500)
        }

        @Test
        fun listShouldBeEmpty() {
            val items = fragmentRule.activity.findViewById<RecyclerView>(R.id.recyclerView)
                .adapter?.itemCount
            Assert.assertEquals(0, items)
        }
    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun getTestShows(): List<Show> {
        val array = mutableListOf<Show>()

        for (i in 0..SHOW_NUMBER) {
            array.add(
                Show(
                    i, "name_$i", "overview_$i",
                    100 - i.toFloat(), "image_path_$i", true
                )
            )
        }

        return array
    }
}