package com.vasylstoliarchuk.reddittops.data.remote.model

import com.google.gson.annotations.SerializedName

data class RetrofitListing<T>(

	@field:SerializedName("data")
	val data: T? = null,

	@field:SerializedName("kind")
	val kind: String? = null
)