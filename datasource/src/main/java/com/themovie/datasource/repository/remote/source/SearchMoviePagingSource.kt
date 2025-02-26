package com.themovie.datasource.repository.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.repository.remote.MovieRemoteSource
import com.themovie.core.network.Result

class SearchMoviePagingSource(
    private val remote: MovieRemoteSource,
    private val query: String = ""
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: STARTING_PAGE
        return when (val result = remote.searchMovie(query, page)) {
            is Result.Success -> {
                LoadResult.Page(
                    data = result.data,
                    prevKey = if (page == STARTING_PAGE) null else page - 1,
                    nextKey = if (result.data.isEmpty() ) null else page + 1
                )
            }
            is Result.Error -> LoadResult.Error(result.exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    companion object {
        private const val STARTING_PAGE = 1
    }
}