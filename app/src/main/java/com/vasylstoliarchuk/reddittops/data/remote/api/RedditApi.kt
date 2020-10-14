package com.vasylstoliarchuk.reddittops.data.remote.api

import com.vasylstoliarchuk.reddittops.data.remote.model.RetrofitListing
import com.vasylstoliarchuk.reddittops.data.remote.model.RetrofitListingData
import retrofit2.http.GET
import retrofit2.http.QueryMap

@JvmSuppressWildcards
interface RedditApi {

    @GET("/top.json")
    suspend fun getTopPosts(@QueryMap queryParams: Map<String, Any>): RetrofitListing<RetrofitListingData>

}