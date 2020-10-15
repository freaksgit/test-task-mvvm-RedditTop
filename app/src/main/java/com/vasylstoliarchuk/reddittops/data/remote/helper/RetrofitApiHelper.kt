package com.vasylstoliarchuk.reddittops.data.remote.helper

import com.google.gson.Gson
import com.vasylstoliarchuk.reddittops.data.Resource
import com.vasylstoliarchuk.reddittops.data.remote.model.RetrofitApiError
import okhttp3.ResponseBody
import retrofit2.HttpException

class RetrofitApiHelper(private val gson: Gson, private val errorMessageProvider: ErrorMessageProvider) : ApiHelper {

    override suspend fun <T : Any> safeExecute(call: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(call())
        } catch (e: Exception) {
            getFailure(e)
        }
    }

    private fun getFailure(e: Exception): Resource.Failure {
        val defaultMessage = errorMessageProvider.getErrorMessage(e)

        return when (e) {
            is HttpException -> {
                val apiError = extractErrorResponse(e.response()?.errorBody())
                apiError?.toFailure(e, defaultMessage) ?: Resource.Failure(statusCode = e.code(), message = e.message() ?: defaultMessage, cause = e)
            }
            else -> Resource.Failure(cause = e, message = defaultMessage)
        }
    }


    private fun extractErrorResponse(errorBody: ResponseBody?): RetrofitApiError? {
        errorBody ?: return null
        return try {
            gson.fromJson(errorBody.string(), RetrofitApiError::class.java)
        } catch (e: Exception) {
            null
        }
    }
}