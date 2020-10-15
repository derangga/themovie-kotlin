package com.aldebaran.data.network.source

import androidx.paging.PagingSource
import com.aldebaran.domain.Result.Status.*
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class UpcomingPagingSource(
    private val remote: MovieRemoteSource
): PagingSource<Int, MovieResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val page = params.key ?: STARTING_PAGE
        val result = remote.getUpcomingMovie(page)

        return when (result.status) {
            SUCCESS -> {
                LoadResult.Page(
                    data = result.data?.results.orEmpty(),
                    prevKey = if (page == STARTING_PAGE) null else page - 1,
                    nextKey = if (result.data?.results.isNullOrEmpty()) null else page + 1
                )
            }
            else -> { LoadResult.Error(Exception(result.message)) }
        }
    }

    companion object {
        private const val STARTING_PAGE = 1
    }
}