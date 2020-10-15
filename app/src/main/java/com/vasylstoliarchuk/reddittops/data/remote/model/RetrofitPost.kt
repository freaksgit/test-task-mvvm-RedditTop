package com.vasylstoliarchuk.reddittops.data.remote.model

import com.google.gson.annotations.SerializedName

data class RetrofitPost(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("author")
    val author: String? = null,

    @field:SerializedName("created_utc")
    val createdUtc: Long? = null,

    @field:SerializedName("thumbnail")
    val thumbnail: String? = null,

    @field:SerializedName("thumbnail_height")
    val thumbnailHeight: Int? = null,

    @field:SerializedName("thumbnail_width")
    val thumbnailWidth: Int? = null,

    @field:SerializedName("num_comments")
    val numComments: Int? = null,

    @field:SerializedName("url_overridden_by_dest")
    val urlOverriddenByDest: String? = null
)