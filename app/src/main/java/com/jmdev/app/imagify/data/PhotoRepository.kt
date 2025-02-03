package com.jmdev.app.imagify.data

import com.jmdev.app.imagify.model.SearchPhotosResult
import com.jmdev.app.imagify.model.photo.FeedPhoto
import com.jmdev.app.imagify.model.unsplashphoto.Photo
import com.jmdev.app.imagify.network.UnsplashAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(private val service: UnsplashAPI) {

    suspend fun getFeedPhotos(page: Int): List<FeedPhoto> {
        val response = service.getPhotosEditorial(page)
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }

    suspend fun searchPhoto(query: String, page: Int): SearchPhotosResult {
        val response = service.searchPhotos(query, page)
        return response.body() ?: SearchPhotosResult(0, 0, emptyList())
    }

    suspend fun getPhoto(id: String): Photo? {
        val response = service.getPhoto(id)
        return response.body()
    }
}