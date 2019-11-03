package com.svprdga.infinitescrollsample.data.persistence.dao

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.svprdga.infinitescrollsample.data.persistence.entity.ShowDbEntity
import com.svprdga.infinitescrollsample.domain.Show
import de.bechte.junit.runners.context.HierarchicalContextRunner
import io.reactivex.observers.TestObserver
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.IllegalStateException

@RunWith(HierarchicalContextRunner::class)
class ShowDaoTest {

    // *************************************** MOCK VARS *************************************** //

    @Mock
    lateinit var realm: Realm
    @Mock
    lateinit var realmQuery: RealmQuery<ShowDbEntity>
    @Mock
    lateinit var realmResults: RealmResults<ShowDbEntity>
    @Mock
    lateinit var entity: ShowDbEntity
    @Mock
    lateinit var show: Show

    // ****************************************** VARS ***************************************** //

    lateinit var dao: ShowDao
    val exception = IllegalStateException("error")

    // ***************************************** SET UP **************************************** //

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        dao = ShowDao(realm)
    }

    // ***************************************** TESTS ***************************************** //

    inner class `when calling findAll()` {

        inner class `given no elements` {

            @Before
            fun setUp() {
                whenever(realm.where(ShowDbEntity::class.java)).thenReturn(realmQuery)
                whenever(realmQuery.findAll()).thenReturn(null)
            }

            @Test
            fun `should return an empty array`() {
                assertTrue(dao.findAll().isEmpty())
            }

        }

    }

    inner class `when calling findAllAsync()` {

        inner class `given no elements` {

            private lateinit var single: TestObserver<Array<ShowDbEntity>>

            @Before
            fun setUp() {
                whenever(realm.where(ShowDbEntity::class.java)).thenReturn(realmQuery)
                whenever(realmQuery.findAll()).thenReturn(null)

                single = dao.findAllAsync().test()
            }

            @After
            fun finalize() {
                single.dispose()
            }

            @Test
            fun `should emit an empty array`() {
                single.assertValue { array ->
                    array.isEmpty()
                }
            }
        }
    }

    inner class `when calling insert()` {

        inner class `given correct insert` {

            @Test
            fun `should complete with the transaction commited`() {
                val completable = dao.insert(entity).test()
                completable.assertOf {
                    verify(realm).commitTransaction()
                }
                completable.dispose()
            }
        }

        inner class `given insert error` {

            private lateinit var completable: TestObserver<Void>

            @Before
            fun setUp() {
                whenever(realm.copyToRealm(entity)).thenThrow(exception)
                completable = dao.insert(entity).test()
            }

            @After
            fun finalize() {
                completable.dispose()
            }

            @Test
            fun `should emit error with the transaction commited`() {
                completable.assertOf {
                    verify(realm).commitTransaction()
                }
            }

            @Test
            fun `should emit exception`() {
                completable.assertError(exception)
            }
        }
    }

    inner class `when calling delete()` {

        private lateinit var completable: TestObserver<Void>

        @Before
        fun setUp() {
            whenever(realm.where(ShowDbEntity::class.java)).thenReturn(realmQuery)
            whenever(realmQuery.equalTo("id", show.id)).thenReturn(realmQuery)
            whenever(realmQuery.findAll()).thenReturn(realmResults)
        }

        inner class `given correct delete` {

            @Before
            fun setUp() {
                completable = dao.delete(show).test()
            }

            @After
            fun finalize() {
                completable.dispose()
            }

            @Test
            fun `should complete with the transaction commited`() {
                completable.assertOf {
                    verify(realm).commitTransaction()
                }
            }
        }

        inner class `given delete error` {

            @Before
            fun setUp() {
                whenever(realmResults.deleteAllFromRealm()).thenThrow(exception)

                completable = dao.delete(show).test()
            }

            @After
            fun finalize() {
                completable.dispose()
            }

            @Test
            fun `should emit error with the transaction commited`() {
                completable.assertOf {
                    verify(realm).commitTransaction()
                }
            }

            @Test
            fun `should emit error`() {
                completable.assertError(exception)
            }
        }
    }

}