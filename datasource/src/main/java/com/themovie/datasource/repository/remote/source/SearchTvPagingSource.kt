package com.themovie.datasource.repository.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.themovie.datasource.entities.ui.Tv
import com.themovie.datasource.repository.remote.TvRemoteSource
import com.themovie.core.network.Result

class SearchTvPagingSource(
    private val remote: TvRemoteSource,
    private val query: String = ""
) : PagingSource<Int, Tv>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tv> {
        val page = params.key ?: STARTING_PAGE
        return when (val result = remote.searchTv(query, page)) {
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

    override fun getRefreshKey(state: PagingState<Int, Tv>): Int? {
        return null
    }

    companion object {
        private const val STARTING_PAGE = 1
    }
}