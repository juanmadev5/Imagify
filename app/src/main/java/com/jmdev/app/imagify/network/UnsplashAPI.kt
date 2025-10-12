package com.jmdev.app.imagify.network

import com.jmdev.app.imagify.DEFAULT_PHOTO_ORIENTATION
import com.jmdev.app.imagify.model.SearchPhotosResult
import com.jmdev.app.imagify.model.photo.FeedPhoto
import com.jmdev.app.imagify.model.unsplashphoto.Photo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashAPI {

    @GET("/photos")
    suspend fun getPhotosEditorial(
        @Query("page") page: Int,
    ): Response<List<FeedPhoto>>

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("orientation") orientation: String = DEFAULT_PHOTO_ORIENTATION,
    ): Response<SearchPhotosResult>

    @GET("/photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: String,
    ): Response<Photo>
}