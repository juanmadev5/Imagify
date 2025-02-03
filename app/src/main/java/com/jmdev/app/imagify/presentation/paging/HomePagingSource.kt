package com.jmdev.app.imagify.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jmdev.app.imagify.data.PhotoRepository
import com.jmdev.app.imagify.model.photo.FeedPhoto

class HomePagingSource(
    private val photoRepository: PhotoRepository,
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
            val response = photoRepository.getFeedPhotos(page)
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}