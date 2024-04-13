package com.jmdev.app.imagify.api

import com.jmdev.app.imagify.App
import com.jmdev.app.imagify.model.photo.Photo
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashAPI {
    @Headers(
        "Accept-Version: v1",
        "Authorization: Client-ID ${App.API_KEY}"
    )
    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = App.DEFAULT_MAX_QUANTITY,
        @Query("order_by") orderBy: String
    ): List<Photo>

    @Headers(
        "Accept-Version: v1",
        "Authorization: Client-ID ${App.API_KEY}"
    )
    @GET("/photos/random")
    suspend fun getRandomPhotos(
        @Query("orientation") orientation: String,
        @Query("count") count: Int = App.DEFAULT_MAX_QUANTITY
    ): List<Photo>

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