package com.vasylstoliarchuk.reddittops.data.remote.helper

import android.content.res.Resources
import com.vasylstoliarchuk.reddittops.R
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorMessageProviderImpl(private val resources: Resources) : ErrorMessageProvider {
    override fun getErrorMessage(exception: Throwable): String {
        val messageResId = when (exception) {
            is UnknownHostException -> R.string.generic_error_no_network
            is SocketTimeoutException -> R.string.generic_error_timeout
            else -> R.string.generic_error
        }

        return resources.getString(messageResId)
    }
}