package com.vasylstoliarchuk.reddittops.ui.image

import android.graphics.Bitmap

data class SaveImageRequest(
    val bitmap: Bitmap,
    val format: Bitmap.CompressFormat,
    val mimeType: String,
    val displayName: String,
    val relativeLocation: String?
)