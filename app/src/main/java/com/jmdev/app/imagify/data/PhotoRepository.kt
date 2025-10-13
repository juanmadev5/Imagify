package com.jmdev.app.imagify.data

import com.jmdev.app.imagify.model.SearchPhotosResult
import com.jmdev.app.imagify.model.photo.FeedPhoto
import com.jmdev.app.imagify.model.photo.User
import com.jmdev.app.imagify.model.unsplashphoto.Photo
import com.jmdev.app.imagify.network.UnsplashAPI

class PhotoRepository(private val service: UnsplashAPI) {

    suspend fun getFeedPhotos(page: Int): List<FeedPhoto> {
        val response = service.getPhotosEditorial(page)
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }

    suspend fun searchPhoto(query: String, page: Int, orientation: String): SearchPhotosResult {
        val response = service.searchPhotos(query = query, page = page, orientation = orientation)
        return response.body() ?: SearchPhotosResult(0, 0, emptyList())
    }

    suspend fun getPhoto(id: String): Photo? {
        val response = service.getPhoto(id)
        return response.body()
    }

    suspend fun getUserProfile(username: String): User? {
        val response = service.getUserProfile(username)
        return response.body()
    }
}