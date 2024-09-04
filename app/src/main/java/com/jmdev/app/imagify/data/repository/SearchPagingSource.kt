package com.jmdev.app.imagify.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jmdev.app.imagify.model.Photo
import com.jmdev.app.imagify.network.UnsplashAPI

class SearchPagingSource(
    private val apiService: UnsplashAPI,
    private val query: String,
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val nextNumber = params.key ?: 1
            val response = apiService.searchPhotos(query = query, page = nextNumber)
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextNumber == 1) null else nextNumber - 1,
                nextKey = if (response.results.isEmpty()) {
                    null
                } else {
                    if (nextNumber < response.totalPages) {
                        nextNumber + 1
                    } else {
                        nextNumber
                    }
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}