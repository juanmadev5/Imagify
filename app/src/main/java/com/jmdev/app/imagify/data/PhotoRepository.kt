package com.jmdev.app.imagify.data

import com.jmdev.app.imagify.api.UnsplashAPI
import com.jmdev.app.imagify.model.photo.Photo
import javax.inject.Inject


class PhotoRepository @Inject constructor(private val unsplashAPI: UnsplashAPI) {

    suspend fun getPhotos(page: Int, orderBy: String): Result<List<Photo>> {
        return try {
            val data = unsplashAPI.getPhotos(page = page, orderBy = orderBy)
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRandomPhotos(orientation: String): Result<List<Photo>> {
        return try {
            val data = unsplashAPI.getRandomPhotos(orientation = orientation)
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