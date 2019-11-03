package com.svprdga.infinitescrollsample.data.persistence.dao

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.svprdga.infinitescrollsample.data.persistence.entity.ShowDbEntity
import com.svprdga.infinitescrollsample.domain.Show
import de.bechte.junit.runners.context.HierarchicalContextRunner
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import org.junit.Assert.assertEquals
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

            @Before
            fun setUp() {
                whenever(realm.where(ShowDbEntity::class.java)).thenReturn(realmQuery)
                whenever(realmQuery.findAll()).thenReturn(null)
            }

            @Test
            fun `should emit an empty array`(){
                dao.findAllAsync().subscribe(object : SingleObserver<Array<ShowDbEntity>> {
                    override fun onSuccess(array: Array<ShowDbEntity>) {
                        assertTrue(array.isEmpty())
                    }

                    override fun onError(e: Throwable) {
                        assertTrue(false)
                    }

                    override fun onSubscribe(d: Disposable) {
                        // Nothing.
                    }
                })
            }
        }
    }

    inner class `when calling insert()` {

        inner class `given correct insert` {

            @Test
            fun `should complete with the transaction commited`() {
                dao.insert(entity).subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        verify(realm).commitTransaction()
                    }

                    override fun onError(e: Throwable) {
                        assertTrue(false)
                    }

                    override fun onSubscribe(d: Disposable) {
                        // Nothing.
                    }
                })
            }
        }

        inner class `given insert error` {

            @Test
            fun `should emit error with the transaction commited`() {
                whenever(realm.copyToRealm(entity)).thenThrow(exception)

                dao.insert(entity).subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        assertTrue(false)
                    }

                    override fun onError(e: Throwable) {
                        assertEquals(exception, e)
                        verify(realm).commitTransaction()
                    }

                    override fun onSubscribe(d: Disposable) {
                        // Nothing.
                    }
                })
            }
        }
    }

    inner class `when calling delete()` {

        @Before
        fun setUp() {
            whenever(realm.where(ShowDbEntity::class.java)).thenReturn(realmQuery)
            whenever(realmQuery.equalTo("id", show.id)).thenReturn(realmQuery)
            whenever(realmQuery.findAll()).thenReturn(realmResults)
        }

        inner class `given correct delete` {

            @Test
            fun `should complete with the transaction commited`() {
                dao.delete(show).subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        verify(realm).commitTransaction()
                    }

                    override fun onError(e: Throwable) {
                        assertTrue(false)
                    }

                    override fun onSubscribe(d: Disposable) {
                        // Nothing.
                    }
                })
            }
        }

        inner class `given delete error` {

            @Test
            fun `should emit error with the transaction commited`() {
                whenever(realmResults.deleteAllFromRealm()).thenThrow(exception)

                dao.delete(show).subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        assertTrue(false)
                    }

                    override fun onError(e: Throwable) {
                        assertEquals(exception, e)
                        verify(realm).commitTransaction()
                    }

                    override fun onSubscribe(d: Disposable) {
                        // Nothing.
                    }
                })
            }
        }
    }

}