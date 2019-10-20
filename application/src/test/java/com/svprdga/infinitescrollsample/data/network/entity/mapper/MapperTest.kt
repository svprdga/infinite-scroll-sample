package com.svprdga.infinitescrollsample.data.network.entity.mapper

import com.svprdga.infinitescrollsample.data.network.entity.PopularShowsResponse
import com.svprdga.infinitescrollsample.data.network.entity.ShowEntity
import com.svprdga.infinitescrollsample.domain.IMAGE_URL
import com.svprdga.infinitescrollsample.domain.W300
import de.bechte.junit.runners.context.HierarchicalContextRunner
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

const val SHOW_ITEMS = 10
const val TOTAL_PAGES = 50

@RunWith(HierarchicalContextRunner::class)
class MapperTest {

    // ****************************************** VARS ***************************************** //

    lateinit var mapper: Mapper
    lateinit var showResponse: PopularShowsResponse

    // ***************************************** SET UP **************************************** //

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        initializeTestVars()

        mapper = Mapper()
    }

    // ***************************************** TESTS ***************************************** //

    inner class `when calling showResponseToShowData()` {

        @Test
        fun `should map objects`() {
            val showData = mapper.showResponseToShowData().apply(showResponse)

            assertEquals(showResponse.page, showData.page)
            assertFalse(showData.isLastPage)

            for (i in 0..SHOW_ITEMS) {
                assertEquals(showResponse.results[i].id, showData.shows[i].id)
                assertEquals(showResponse.results[i].name, showData.shows[i].name)
                assertEquals(showResponse.results[i].overview, showData.shows[i].overview)
                assertEquals(showResponse.results[i].averageRating, showData.shows[i].averageRating)
                assertEquals(
                    "$IMAGE_URL$W300${showResponse.results[i].imagePath!!.substring(1)}",
                    showData.shows[i].imagePath
                )
            }
        }

        inner class `given last page of results` {

            @Test
            fun `should set last page`() {
                showResponse.page = TOTAL_PAGES
                val showData = mapper.showResponseToShowData().apply(showResponse)
                assertTrue(showData.isLastPage)
            }

        }

    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun initializeTestVars() {
        val showsEntity: ArrayList<ShowEntity> = arrayListOf()

        for (i in 0..SHOW_ITEMS) {
            val entity = ShowEntity()
            entity.id = i
            entity.name = "name_$i"
            entity.overview = "overview_$i"
            entity.averageRating = 100f - i.toFloat()
            entity.imagePath = "path_$i"
            showsEntity.add(entity)
        }

        showResponse = PopularShowsResponse()
        showResponse.page = 1
        showResponse.totalPages = TOTAL_PAGES
        showResponse.results = showsEntity
    }

}