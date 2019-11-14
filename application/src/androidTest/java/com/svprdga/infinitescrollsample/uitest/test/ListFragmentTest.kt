package com.svprdga.infinitescrollsample.uitest.test

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.svprdga.infinitescrollsample.R
import com.svprdga.infinitescrollsample.uitest.custom.createRule
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IListPresenter
import com.svprdga.infinitescrollsample.presentation.presenter.abstraction.IShowPresenter
import com.svprdga.infinitescrollsample.presentation.ui.extra.ShowView
import com.svprdga.infinitescrollsample.presentation.ui.fragment.ListFragment
import com.svprdga.infinitescrollsample.uitest.stub.TestListPresenter
import com.svprdga.infinitescrollsample.uitest.stub.TestShowPresenter
import de.bechte.junit.runners.context.HierarchicalContextRunner
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module

private const val SHOW_NUMBER = 10

@RunWith(HierarchicalContextRunner::class)
class ListFragmentTest : BaseUiTest() {

    // ****************************************** VARS ***************************************** //

    private val listPresenter = TestListPresenter()

    private val fragment = ListFragment()

    @get:Rule
    val fragmentRule = createRule(fragment, module {
        factory<IListPresenter>(override = true) {
            listPresenter
        }
        factory<IShowPresenter>(override = true) {
            TestShowPresenter()
        }
    })

    // ***************************************** SET UP **************************************** //

    @After
    fun after() {
        listPresenter.reset()
    }

    // ***************************************** TESTS ***************************************** //

    inner class WhenViewIsCreated {

        @Before
        fun setUp() {
            fragmentRule.launch()
        }

        @Test
        fun shouldBoundToPresenter() {
            assertEquals(1, listPresenter.bindCalled)
        }

        @Test
        fun shouldCallOnStartInPresenter() {
            assertEquals(1, listPresenter.onStartCalled)
        }
    }

    inner class WhenCallingAppendShows {

        @Before
        fun setUp() {
            fragmentRule.launch()

            fragmentRule.activity.runOnUiThread {
                listPresenter.view?.appendShows(getTestShows())
            }

            waitFor(500)
        }

        @Test
        fun shouldLoadListOfShow() {
            onView(withText("name_0"))
                .check(matches(isDisplayed()))
            onView(withText("name_1"))
                .check(matches(isDisplayed()))
        }

        inner class WhenScrollingLayout {

            @Test
            fun shouldScrollCorrectly() {
                onView(withId(R.id.recyclerView))
                    .perform(
                        RecyclerViewActions.scrollTo<ShowView>
                            (hasDescendant(withText("name_5")))
                    )
                    .check(matches(isDisplayed()))

            }
        }

        inner class WhenScrollToTheBottom {

            @Test
            fun shouldRequestMoreShowsToPresenter() {
                onView(withId(R.id.recyclerView))
                    .perform(
                        RecyclerViewActions.scrollTo<ShowView>
                            (hasDescendant(withText("name_${SHOW_NUMBER - 1}")))
                    )
                assertEquals(1, listPresenter.loadNextShowCalled)
            }
        }

    }

    inner class WhenCallingHideListLayout {

        @Test
        fun shouldHideListLayout() {
            fragmentRule.launch()
            fragmentRule.activity.runOnUiThread {
                listPresenter.view?.hideListLayout()
            }
            waitFor(500)

            onView(withId(R.id.listLayout))
                .check(matches(not(isDisplayed())))
        }
    }

    inner class WhenCallingShowErrorLayout {

        @Before
        fun setUp() {
            fragmentRule.launch()
            fragmentRule.activity.runOnUiThread {
                listPresenter.view?.hideListLayout()
                listPresenter.view?.showErrorLayout()
            }
            waitFor(500)
        }

        @Test
        fun shouldShowErrorLayout() {
            onView(withId(R.id.errorImage))
                .check(matches(isDisplayed()))
            onView(withId(R.id.errorTitle))
                .check(matches(isDisplayed()))
            onView(withId(R.id.errorMessage))
                .check(matches(isDisplayed()))
        }

    }

    /**
     * The error layout is hidden by default. So in order to test this method
     * we are going first to show it, and then hide it. Then we can make assertions.
     */
    inner class WhenCallingHideErrorLayout {

        @Before
        fun setUp() {
            fragmentRule.launch()
            fragmentRule.activity.runOnUiThread {
                listPresenter.view?.hideListLayout()
                listPresenter.view?.showErrorLayout()
                listPresenter.view?.hideErrorLayout()
            }
            waitFor(500)
        }

        @Test
        fun shouldShowErrorLayout() {
            onView(withId(R.id.errorImage))
                .check(matches(not(isDisplayed())))
            onView(withId(R.id.errorTitle))
                .check(matches(not(isDisplayed())))
            onView(withId(R.id.errorMessage))
                .check(matches(not(isDisplayed())))
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
                listPresenter.view?.appendShows(getTestShows())
                listPresenter.view?.clearList()
            }
            waitFor(500)
        }

        @Test
        fun listShouldBeEmpty() {
            val items = fragmentRule.activity.findViewById<RecyclerView>(R.id.recyclerView)
                .adapter?.itemCount
            assertEquals(0, items)
        }
    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun getTestShows(): List<Show> {
        val array = mutableListOf<Show>()

        for (i in 0..SHOW_NUMBER) {
            array.add(
                Show(
                    i, "name_$i", "overview_$i",
                    100 - i.toFloat(), "image_path_$i", false
                )
            )
        }

        return array
    }
}