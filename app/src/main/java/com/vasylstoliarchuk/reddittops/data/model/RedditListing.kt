package com.vasylstoliarchuk.reddittops.data.model

data class RedditListing<T>(
	val data: T? = null,
	val kind: String? = null
)