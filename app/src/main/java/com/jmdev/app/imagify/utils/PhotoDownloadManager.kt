package com.jmdev.app.imagify.utils

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.File
import java.util.concurrent.TimeUnit

class PhotoDownloadManager {

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .build()

    suspend fun downloadPhoto(
        context: Context,
        photoUrl: String,
        fileName: String,
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = httpClient.newCall(Request.Builder().url(photoUrl).build()).execute()
            if (!response.isSuccessful) return@withContext false
            val body = response.body ?: return@withContext false
            val finalFileName = "$fileName.jpg"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveViaMediaStore(context, body, finalFileName)
            } else {
                saveViaFileStream(body, finalFileName)
            }
        } catch (e: Exception) {
            false
        }
    }

    private fun saveViaMediaStore(context: Context, body: ResponseBody, fileName: String): Boolean {
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, "image/jpeg")
            put(MediaStore.Downloads.RELATIVE_PATH, "${Environment.DIRECTORY_DOWNLOADS}/Imagify")
            put(MediaStore.Downloads.IS_PENDING, 1)
        }
        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            ?: return false
        resolver.openOutputStream(uri)?.use { out ->
            body.byteStream().use { it.copyTo(out) }
        }
        contentValues.clear()
        contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
        resolver.update(uri, contentValues, null, null)
        return true
    }

    private fun saveViaFileStream(body: ResponseBody, fileName: String): Boolean {
        val dir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "Imagify"
        )
        dir.mkdirs()
        File(dir, fileName).outputStream().use { out ->
            body.byteStream().use { it.copyTo(out) }
        }
        return true
    }
}
