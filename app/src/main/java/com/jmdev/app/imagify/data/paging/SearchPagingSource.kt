package com.jmdev.app.imagify.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jmdev.app.imagify.data.remote.UnsplashAPI
import com.jmdev.app.imagify.model.photo.FeedPhoto

class SearchPagingSource(
    private val service: UnsplashAPI,
    private val query: String,
    private val orientation: String,
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
            val response = service.searchPhotos(query, page, orientation)
            val results = response.body()?.results ?: emptyList()
            LoadResult.Page(
                data = results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (results.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
