package com.vasylstoliarchuk.reddittops.data.remote.helper

import com.vasylstoliarchuk.reddittops.data.Resource

interface ApiHelper {
    suspend fun <T : Any> safeExecute(call: suspend () -> T): Resource<T>
}