package com.vasylstoliarchuk.reddittops.data.model

data class RedditListingData(
	val modhash: String? = null,
	val children: List<RedditListing<RedditPost>?>? = null,
	val before: String? = null,
	val dist: Int? = null,
	val after: String? = null
)