package com.jmdev.app.imagify.network

import com.jmdev.app.imagify.App
import com.jmdev.app.imagify.model.Photo
import com.jmdev.app.imagify.model.SearchPhotosResult
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashAPI {

    @GET("/photos")
    suspend fun getPhotosEditorial(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = App.DEFAULT_QUANTITY
    ): List<Photo>

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = App.DEFAULT_QUANTITY
    ): SearchPhotosResult
}