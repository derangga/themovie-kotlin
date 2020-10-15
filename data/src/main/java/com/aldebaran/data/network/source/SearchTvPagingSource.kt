package com.aldebaran.data.network.source

import androidx.paging.PagingSource
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.remote.TvResponse
import com.aldebaran.domain.repository.remote.TvRemoteSource

class SearchTvPagingSource(
    private val remote: TvRemoteSource,
    private val query: String = ""
): PagingSource<Int, TvResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvResponse> {
        val page = params.key ?: STARTING_PAGE
        val result = remote.searchTv(query, page)

        return when (result.status) {
            Result.Status.SUCCESS -> { LoadResult.Page(
                data = result.data?.results.orEmpty(),
                prevKey = if (page == STARTING_PAGE) null else page -1,
                nextKey = if (result.data?.results.isNullOrEmpty()) null else page + 1
            )}
            else -> { LoadResult.Error(Exception(result.message)) }
        }
    }

    companion object {
        private const val STARTING_PAGE = 1
    }
}