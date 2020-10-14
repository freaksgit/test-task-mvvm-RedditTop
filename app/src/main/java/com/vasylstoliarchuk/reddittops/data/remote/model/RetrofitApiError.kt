package com.vasylstoliarchuk.reddittops.data.remote.model

import com.google.gson.annotations.SerializedName
import com.vasylstoliarchuk.reddittops.data.Resource
import retrofit2.HttpException

data class RetrofitApiError(
    @field:SerializedName("error")
    val error: String? = null,

    @field:SerializedName("message")
    val message: String? = null

) {

    fun toFailure(cause: Exception? = null): Resource.Failure {
        return Resource.Failure(if (cause is HttpException) cause.code() else null, error, message, cause)
    }
}
