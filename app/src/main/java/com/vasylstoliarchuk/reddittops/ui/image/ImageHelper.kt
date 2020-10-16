package com.vasylstoliarchuk.reddittops.ui.image

import java.io.IOException

interface ImageHelper {

    @Throws(IOException::class)
    suspend fun saveImage(request: SaveImageRequest)
}