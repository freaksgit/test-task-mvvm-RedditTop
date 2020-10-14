package com.vasylstoliarchuk.reddittops.data.remote.model

import com.google.gson.annotations.SerializedName

data class RetrofitListingData(

	@field:SerializedName("modhash")
	val modhash: String? = null,

	@field:SerializedName("children")
	val children: List<RetrofitListing<RetrofitPost>?>? = null,

	@field:SerializedName("before")
	val before: String? = null,

	@field:SerializedName("dist")
	val dist: Int? = null,

	@field:SerializedName("after")
	val after: String? = null
)