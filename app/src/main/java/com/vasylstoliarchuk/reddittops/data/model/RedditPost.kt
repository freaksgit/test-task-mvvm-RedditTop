package com.vasylstoliarchuk.reddittops.data.model

import com.google.gson.annotations.SerializedName

data class RedditPost(
    val id: String? = null,
    val title: String? = null,
    val author: String? = null,
    val createdUtc: Long? = null,
    val thumbnail: String? = null,
    val thumbnailHeight: Int? = null,
    val thumbnailWidth: Int? = null,
    val numComments: Int? = null,
    val urlOverriddenByDest: String? = null
)