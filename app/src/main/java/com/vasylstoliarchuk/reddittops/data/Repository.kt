package com.vasylstoliarchuk.reddittops.data

import com.vasylstoliarchuk.reddittops.data.model.RedditPost

interface Repository {

    suspend fun getTopPosts(refresh: Boolean = false): Resource<List<RedditPost>>
    suspend fun loadMoreTopPosts(): Resource<List<RedditPost>>

}