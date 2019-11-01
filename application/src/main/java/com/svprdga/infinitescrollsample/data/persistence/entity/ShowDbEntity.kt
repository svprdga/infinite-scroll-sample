package com.svprdga.infinitescrollsample.data.persistence.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField

open class ShowDbEntity(
    @PrimaryKey
    open var id: Int = 0,
    open var name: String = "",
    open var overview: String = "",
    @RealmField("average_rating")
    open var averageRating: Float = 0.0F,
    @RealmField("image_path")
    open var imagePath: String? = null
) : RealmObject()