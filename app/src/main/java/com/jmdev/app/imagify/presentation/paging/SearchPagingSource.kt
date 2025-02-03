package com.jmdev.app.imagify.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jmdev.app.imagify.data.PhotoRepository
import com.jmdev.app.imagify.model.photo.FeedPhoto

class SearchPagingSource(
    private val photoRepository: PhotoRepository,
    private val query: String,
    private val orientation: String
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
            val response = photoRepository.searchPhoto(query, page, orientation)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}