package com.vasylstoliarchuk.reddittops.data.remote

import com.vasylstoliarchuk.reddittops.data.model.RedditListing
import com.vasylstoliarchuk.reddittops.data.model.RedditListingData
import com.vasylstoliarchuk.reddittops.data.model.RedditPost
import com.vasylstoliarchuk.reddittops.data.remote.model.RetrofitListing
import com.vasylstoliarchuk.reddittops.data.remote.model.RetrofitListingData
import com.vasylstoliarchuk.reddittops.data.remote.model.RetrofitPost

fun RetrofitListing<RetrofitListingData>.mapToRedditDataListing(): RedditListing<RedditListingData> {
    return RedditListing(
        data = data?.mapToRedditListingData(),
        kind = kind
    )
}

fun RetrofitListing<RetrofitPost>.mapToRedditPostListing(): RedditListing<RedditPost> {
    return RedditListing(
        data = data?.mapToRedditPost(),
        kind = kind
    )
}

fun RetrofitListingData.mapToRedditListingData(): RedditListingData {
    return RedditListingData(
        modhash = modhash,
        children = children?.map { it?.mapToRedditPostListing() },
        before = before,
        dist = dist,
        after = after
    )
}

fun RetrofitPost.mapToRedditPost(): RedditPost {
    return RedditPost(
        id = id,
        title = title,
        author = author,
        createdUtc = createdUtc,
        thumbnail = thumbnail,
        thumbnailWidth = thumbnailWidth,
        thumbnailHeight = thumbnailHeight,
        numComments = numComments
    )
}