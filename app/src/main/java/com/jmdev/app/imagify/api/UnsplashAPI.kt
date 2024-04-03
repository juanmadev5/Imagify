package com.jmdev.app.imagify.api

import com.jmdev.app.imagify.App
import com.jmdev.app.imagify.model.Photo
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashAPI {

    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Photo>

    @GET("/photos/{imageId}")
    suspend fun getPhoto(
        @Path("imageId", encoded = false) imageId: String,
    ): Photo

    @Headers(
        "Accept-Version: v1",
        "Authorization: Client-ID ${App.API_KEY}"
    )
    @GET("/search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Photo>
}