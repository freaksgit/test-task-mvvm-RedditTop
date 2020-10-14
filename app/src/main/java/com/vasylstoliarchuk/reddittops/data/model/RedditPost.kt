package com.vasylstoliarchuk.reddittops.data.model

data class RedditPost(
    val id: String? = null,
    val title: String? = null,
    val author: String? = null,
    val createdUtc: Long? = null,
    val thumbnail: String? = null,
    val numComments: Int? = null,
    val urlOverriddenByDest: String? = null
)