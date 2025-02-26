package com.aldebaran.data.network.source

import androidx.paging.PagingSource
import com.aldebaran.domain.entities.ui.Movie
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import com.aldebaran.network.Result
import java.util.*


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

    companion object {
        private const val STARTING_PAGE = 1
    }
}