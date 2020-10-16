package com.vasylstoliarchuk.reddittops.ui.image

import android.R.attr.mimeType
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.io.OutputStream


class ImageHelperImpl(val context: Context) : ImageHelper {

    override suspend fun saveImage(request: SaveImageRequest) = withContext(Dispatchers.IO) {
        val relativeLocation: String = if (request.relativeLocation != null)
            Environment.DIRECTORY_PICTURES + File.pathSeparator + request.relativeLocation
        else
            Environment.DIRECTORY_PICTURES

        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, request.displayName)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)
        }

        val resolver = context.contentResolver
        var stream: OutputStream? = null
        var uri: Uri? = null
        try {
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            uri = resolver.insert(contentUri, contentValues)
            if (uri == null) {
                throw IOException("Failed to create new MediaStore record.")
            }
            stream = resolver.openOutputStream(uri)
            if (stream == null) {
                throw IOException("Failed to get output stream.")
            }
            if (!request.bitmap.compress(request.format, 95, stream)) {
                throw IOException("Failed to save bitmap.")
            }
        } catch (e: IOException) {
            if (uri != null) {
                resolver.delete(uri, null, null)
            }
            throw e
        } finally {
            stream?.close()
        }
    }
}