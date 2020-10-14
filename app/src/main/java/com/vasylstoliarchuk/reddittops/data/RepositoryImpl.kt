package com.vasylstoliarchuk.reddittops.data

import com.vasylstoliarchuk.reddittops.data.model.RedditPost
import com.vasylstoliarchuk.reddittops.data.remote.RemoteDataSource

class RepositoryImpl(private val remote: RemoteDataSource) : Repository {

    companion object {
        private const val LIMIT = 20
    }

    private val cachedTopPosts = mutableListOf<RedditPost>()
    private var after: String? = null

    override suspend fun getTopPosts(refresh: Boolean): Resource<List<RedditPost>> {
        return when {
            refresh -> {
                cachedTopPosts.clear()
                after = null
                loadMoreTopPosts()
            }
            cachedTopPosts.isEmpty() -> loadMoreTopPosts()
            else -> Resource.Success(cachedTopPosts)
        }
    }

    override suspend fun loadMoreTopPosts(): Resource<List<RedditPost>> {
        return when (val topPosts = remote.getTopPosts(after, LIMIT)) {
            is Resource.Success -> {
                val data = topPosts.value.data
                val posts = data?.children?.mapNotNull { it?.data } ?: emptyList()
                after = data?.after
                cachedTopPosts.addAll(posts)
                Resource.Success(posts)
            }
            is Resource.Failure -> return topPosts
            Resource.Loading -> throw IllegalStateException("Illegal Resource value")
        }
    }
}