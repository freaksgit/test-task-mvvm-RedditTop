package com.vasylstoliarchuk.reddittops.data.remote

import com.vasylstoliarchuk.reddittops.data.Resource
import com.vasylstoliarchuk.reddittops.data.model.RedditListing
import com.vasylstoliarchuk.reddittops.data.model.RedditListingData

interface RemoteDataSource {
    suspend fun getTopPosts(after: String? = null, limit: Int): Resource<RedditListing<RedditListingData>>
}