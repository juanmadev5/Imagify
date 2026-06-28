package com.jmdev.app.imagify.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jmdev.app.imagify.data.remote.UnsplashAPI
import com.jmdev.app.imagify.model.photo.FeedPhoto

class HomePagingSource(
    private val service: UnsplashAPI,
) : PagingSource<Int, FeedPhoto>() {

    override fun getRefreshKey(state: PagingState<Int, FeedPhoto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FeedPhoto> {
        return try {
            val page = params.key ?: 1
            val response = service.getPhotosEditorial(page)
            val data = if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
