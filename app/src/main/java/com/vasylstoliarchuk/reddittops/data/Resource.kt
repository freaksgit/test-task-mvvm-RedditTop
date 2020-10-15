package com.vasylstoliarchuk.reddittops.data

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val value: T) : Resource<T>()
    data class Failure(val statusCode: Int? = null, val error: String? = null, val message: String, val cause: Exception? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

inline fun <T : Any, R : Any> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(this.value))
        is Resource.Failure -> Resource.Failure(this.statusCode, this.error, this.message, this.cause)
        is Resource.Loading -> Resource.Loading
    }
}