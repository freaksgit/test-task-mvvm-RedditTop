package com.vasylstoliarchuk.reddittops.ui.toplist.adapter

import android.annotation.SuppressLint
import android.text.format.DateUtils
import com.vasylstoliarchuk.reddittops.data.model.RedditPost
import java.util.*

data class TopPostsItem(
    val id: String,
    val itemId:Long,
    val title: String,
    val author: String,
    val createdUtc: String,
    val thumbnail: String?,
    val thumbnailHeight: Int?,
    val thumbnailWidth: Int?,
    val numComments: Int,
    val urlOverriddenByDest: String?
) {
    companion object {
        fun from(redditPost: RedditPost): TopPostsItem {
            return TopPostsItem(
                id = redditPost.id ?: "",
                itemId = redditPost.id.hashCode().toLong(),
                title = redditPost.title ?: "",
                author = redditPost.author ?: "",
                createdUtc = formatCreatedDate(redditPost.createdUtc),
                thumbnail = redditPost.thumbnail,
                thumbnailHeight = redditPost.thumbnailHeight,
                thumbnailWidth = redditPost.thumbnailWidth,
                numComments = redditPost.numComments ?: 0,
                urlOverriddenByDest = redditPost.urlOverriddenByDest
            )
        }

        @SuppressLint("SimpleDateFormat")
        fun formatCreatedDate(createdUtc: Long?): String {
            createdUtc ?: return ""
            val now = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
            return DateUtils.getRelativeTimeSpanString(createdUtc * 1000, now, DateUtils.MINUTE_IN_MILLIS).toString()
        }
    }
}