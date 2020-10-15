package com.vasylstoliarchuk.reddittops.data.remote.helper


interface ErrorMessageProvider {

    fun getErrorMessage(exception: Throwable):String
}