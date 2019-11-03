package com.svprdga.infinitescrollsample.data.network.entity.mapper

import com.svprdga.infinitescrollsample.data.network.entity.PopularShowsResponse
import com.svprdga.infinitescrollsample.data.network.entity.ShowEntity
import com.svprdga.infinitescrollsample.data.persistence.entity.ShowDbEntity
import com.svprdga.infinitescrollsample.domain.IMAGE_URL
import com.svprdga.infinitescrollsample.domain.Show
import com.svprdga.infinitescrollsample.domain.ShowData
import com.svprdga.infinitescrollsample.domain.W300
import de.bechte.junit.runners.context.HierarchicalContextRunner
import junit.framework.Assert.*
import okhttp3.internal.notifyAll
import okhttp3.internal.waitMillis
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
    lateinit var entities: ArrayList<ShowEntity>
    lateinit var showData: ShowData
    lateinit var showsDbEntities: Array<ShowDbEntity>

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
                showResponse = PopularShowsResponse(TOTAL_PAGES, TOTAL_PAGES, entities)
                val showData = mapper.showResponseToShowData().apply(showResponse)
                assertTrue(showData.isLastPage)
            }

        }

    }

    inner class `when calling showToShowDbEntity()` {

        @Test
        fun `should return mapped ShowDbEntity`() {
            val show = showData.shows[0]
            val entity = mapper.showToShowDbEntity(show)

            assertEquals(show.id, entity.id)
            assertEquals(show.name, entity.name)
            assertEquals(show.overview, entity.overview)
            assertEquals(show.imagePath, entity.imagePath)
            assertEquals(show.averageRating, entity.averageRating)
        }
    }

    inner class `when calling showDbEntitiesToShows()` {

        @Test
        fun `should return mapped objects`() {
            val list = mapper.showDbEntitiesToShows(showsDbEntities)

            for (i in 0..SHOW_ITEMS) {
                assertEquals(showsDbEntities[i].id, list[i].id)
                assertEquals(showsDbEntities[i].name, list[i].name)
                assertEquals(showsDbEntities[i].overview, list[i].overview)
                assertEquals(showsDbEntities[i].imagePath, list[i].imagePath)
                assertEquals(showsDbEntities[i].averageRating, list[i].averageRating)
            }
        }
    }

    inner class `when calling showDbEntitiesToShowsAsync()` {

        @Test
        fun `should return mapped objects`() {
            val list = mapper.showDbEntitiesToShowsAsync().apply(showsDbEntities)

            for (i in 0..SHOW_ITEMS) {
                assertEquals(showsDbEntities[i].id, list[i].id)
                assertEquals(showsDbEntities[i].name, list[i].name)
                assertEquals(showsDbEntities[i].overview, list[i].overview)
                assertEquals(showsDbEntities[i].imagePath, list[i].imagePath)
                assertEquals(showsDbEntities[i].averageRating, list[i].averageRating)
            }
        }
    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun initializeTestVars() {
        entities = arrayListOf()
        val shows: ArrayList<Show> = arrayListOf()
        val dbEntities: MutableList<ShowDbEntity> = arrayListOf()

        for (i in 0..SHOW_ITEMS) {
            val entity = ShowEntity(
                i,
                "name_$i",
                "overview_$i",
                100f - i.toFloat(),
                "path_$i"
            )
            entities.add(entity)

            shows.add(
                Show(
                    entity.id,
                    entity.name,
                    entity.overview,
                    entity.averageRating,
                    entity.imagePath, false
                )
            )

            dbEntities.add(
                ShowDbEntity(
                    entity.id,
                    entity.name,
                    entity.overview,
                    entity.averageRating,
                    entity.imagePath
                )
            )
        }

        showResponse = PopularShowsResponse(1, TOTAL_PAGES, entities)
        showData = ShowData(1, false, shows)
        showsDbEntities = dbEntities.toTypedArray()
    }

}