package com.jmdev.app.imagify.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jmdev.app.imagify.model.Photo
import com.jmdev.app.imagify.network.UnsplashAPI

class HomePagingSource(
    private val apiService: UnsplashAPI,
) : PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = apiService.getPhotosEditorial(page = nextPageNumber)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}