package com.themovie.datasource.repository.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.repository.remote.MovieRemoteSource
import com.themovie.core.network.Result
import java.util.Calendar


class MoviePagingSource(
    private val remote: MovieRemoteSource,
    private val genre: String
) : PagingSource<Int, Movie>() {

    private val calendar by lazy { Calendar.getInstance() }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: STARTING_PAGE
        val result = remote.getDiscoverMovie(genre, calendar.get(Calendar.YEAR), page)

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

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    companion object {
        private const val STARTING_PAGE = 1
    }
}