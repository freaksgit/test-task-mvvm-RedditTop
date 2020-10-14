package com.vasylstoliarchuk.reddittops.data

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val value: T) : Resource<T>()
    data class Failure(val statusCode: Int? = null, val error: String? = null, val message: String? = null, val cause: Exception? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}