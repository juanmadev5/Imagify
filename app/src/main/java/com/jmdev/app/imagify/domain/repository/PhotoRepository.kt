package com.jmdev.app.imagify.domain.repository

import androidx.paging.PagingData
import com.jmdev.app.imagify.model.photo.FeedPhoto
import com.jmdev.app.imagify.model.photo.User
import com.jmdev.app.imagify.model.unsplashphoto.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun getFeedPhotos(): Flow<PagingData<FeedPhoto>>
    fun searchPhotos(query: String, orientation: String): Flow<PagingData<FeedPhoto>>
    suspend fun getPhoto(id: String): Photo?
    suspend fun getUserProfile(username: String): User?
}
