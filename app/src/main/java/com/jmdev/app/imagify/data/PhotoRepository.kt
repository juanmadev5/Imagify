package com.jmdev.app.imagify.data

import com.jmdev.app.imagify.api.UnsplashAPI
import com.jmdev.app.imagify.model.Photo
import javax.inject.Inject


class PhotoRepository @Inject constructor(private val unsplashAPI: UnsplashAPI) {

    suspend fun getPhotos(page: Int, perPage: Int): Result<List<Photo>> {
        return try {
            val data = unsplashAPI.getPhotos(page, perPage)
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPhoto(imageId: String): Result<Photo> {
        return try {
            val data = unsplashAPI.getPhoto(imageId)
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchPhotos(query: String, page: Int, perPage: Int): Result<List<Photo>> {
        return try {
            val data = unsplashAPI.searchPhotos(query, page, perPage)
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}