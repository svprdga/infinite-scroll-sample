package com.svprdga.infinitescrollsample.data.persistence.dao

import com.svprdga.infinitescrollsample.data.persistence.entity.ShowDbEntity
import com.svprdga.infinitescrollsample.domain.Mockable
import com.svprdga.infinitescrollsample.domain.Show
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.realm.Realm

@Mockable
class ShowDao(
    private val realm: Realm
) {

    /**
     * Find all the favorite [ShowDbEntity].
     * @return [Single] with an [Array] of found [ShowDbEntity].
     */
    fun findAll(): Single<Array<ShowDbEntity>> {
        return object : Single<Array<ShowDbEntity>>() {
            override fun subscribeActual(observer: SingleObserver<in Array<ShowDbEntity>>) {
                val entities = realm.where(ShowDbEntity::class.java).findAll()

                if (entities != null) {
                    observer.onSuccess(entities.toTypedArray())
                } else {
                    observer.onSuccess(emptyArray())
                }
            }
        }
    }

    /**
     * Insert a new [ShowDbEntity].
     * @param entity [ShowDbEntity] to persist.
     * @return [Completable] which will always call #onComplete.
     */
    fun insert(entity: ShowDbEntity): Completable {
        return try {
            realm.beginTransaction()
            realm.copyToRealm(entity)
            Completable.complete()
        } catch (e: Exception) {
            Completable.error(e)
        } finally {
            realm.commitTransaction()
        }
    }

    /**
     * Remove a [ShowDbEntity].
     * @param show [Show] to remove.
     * @return [Completable] which will always complete after the deletion.
     */
    fun delete(show: Show): Completable {
        return try {
            val result =
                realm.where(ShowDbEntity::class.java).equalTo("id", show.id).findAll()
            realm.beginTransaction()
            result.deleteAllFromRealm()
            Completable.complete()
        } catch (e: Exception) {
            Completable.error(e)
        } finally {
            realm.commitTransaction()
        }
    }

}