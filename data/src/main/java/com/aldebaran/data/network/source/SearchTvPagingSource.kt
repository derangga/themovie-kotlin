package com.aldebaran.data.network.source

import androidx.paging.PagingSource
import com.aldebaran.domain.entities.ui.Tv
import com.aldebaran.domain.repository.remote.TvRemoteSource
import com.aldebaran.network.Result

class SearchTvPagingSource(
    private val remote: TvRemoteSource,
    private val query: String = ""
) : PagingSource<Int, Tv>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tv> {
        val page = params.key ?: STARTING_PAGE
        val result = remote.searchTv(query, page)

        return when (result) {
            is Result.Success -> {
                LoadResult.Page(
                    data = result.data,
                    prevKey = if (page == STARTING_PAGE) null else page - 1,
                    nextKey = if (result.data.isEmpty()) null else page + 1
                )
            }
            is Result.Error -> LoadResult.Error(result.exception)

        }
    }

    companion object {
        private const val STARTING_PAGE = 1
    }
}