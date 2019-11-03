package com.svprdga.infinitescrollsample.presentation.eventbus

import com.svprdga.infinitescrollsample.domain.Show
import de.bechte.junit.runners.context.HierarchicalContextRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HierarchicalContextRunner::class)
class BusTest {

    // ****************************************** VARS ***************************************** //

    lateinit var favoritesBus: FavoritesBus

    // ***************************************** SET UP **************************************** //

    @Before
    fun setUp() {
        favoritesBus = FavoritesBus()
    }

    // ***************************************** TESTS ***************************************** //

    @Test
    fun `test FavoritesBus`() {
        val expected = FavoriteEvent(
            Show(
                22,
                "test_show",
                "overview",
                0.54F,
                "path",
                true
            ), 245
        )

        val observer = favoritesBus.getFavoriteEvent().test()
        favoritesBus.setFavoriteEvent(expected)
        observer.assertValue(expected)
    }

}