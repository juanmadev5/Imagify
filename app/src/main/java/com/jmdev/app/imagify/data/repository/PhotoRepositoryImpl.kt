package com.jmdev.app.imagify.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jmdev.app.imagify.data.paging.HomePagingSource
import com.jmdev.app.imagify.data.paging.SearchPagingSource
import com.jmdev.app.imagify.data.remote.UnsplashAPI
import com.jmdev.app.imagify.domain.repository.PhotoRepository
import com.jmdev.app.imagify.model.photo.FeedPhoto
import com.jmdev.app.imagify.model.photo.User
import com.jmdev.app.imagify.model.unsplashphoto.Photo
import kotlinx.coroutines.flow.Flow

class PhotoRepositoryImpl(private val service: UnsplashAPI) : PhotoRepository {

    override fun getFeedPhotos(): Flow<PagingData<FeedPhoto>> =
        Pager(PagingConfig(pageSize = 4)) {
            HomePagingSource(service)
        }.flow

    override fun searchPhotos(query: String, orientation: String): Flow<PagingData<FeedPhoto>> =
        Pager(PagingConfig(pageSize = 4)) {
            SearchPagingSource(service, query, orientation)
        }.flow

    override suspend fun getPhoto(id: String): Photo? = service.getPhoto(id).body()

    override suspend fun getUserProfile(username: String): User? = service.getUserProfile(username).body()
}
