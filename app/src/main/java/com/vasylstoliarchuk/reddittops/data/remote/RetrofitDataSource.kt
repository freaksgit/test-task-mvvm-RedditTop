package com.vasylstoliarchuk.reddittops.data.remote

import com.vasylstoliarchuk.reddittops.data.Resource
import com.vasylstoliarchuk.reddittops.data.model.RedditListing
import com.vasylstoliarchuk.reddittops.data.model.RedditListingData
import com.vasylstoliarchuk.reddittops.data.remote.api.RedditApi
import com.vasylstoliarchuk.reddittops.data.remote.helper.ApiHelper

class RetrofitDataSource(private val api: RedditApi, private val apiHelper: ApiHelper) : RemoteDataSource {

    override suspend fun getTopPosts(after: String?, limit: Int): Resource<RedditListing<RedditListingData>> {
        return apiHelper.safeExecute {
            val queryParams = mutableMapOf<String, Any>("limit" to limit)
            if (!after.isNullOrBlank()) {
                queryParams["after"] = after
            }
            api.getTopPosts(queryParams).mapToRedditDataListing()
        }
    }
}