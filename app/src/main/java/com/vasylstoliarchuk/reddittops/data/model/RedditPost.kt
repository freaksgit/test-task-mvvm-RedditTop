package com.vasylstoliarchuk.reddittops.data.model


data class RedditPost(
    val id: String?,
    val title: String?,
    val author: String?,
    val createdUtc: Long?,
    val thumbnail: String?,
    val thumbnailHeight: Int?,
    val thumbnailWidth: Int?,
    val numComments: Int?
)