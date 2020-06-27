package com.themovie.localdb

import androidx.lifecycle.LiveData
import com.themovie.model.db.*

interface LocalSource {
    suspend fun insertTrendMovie(trending: List<Trending>)
    suspend fun updateTrendingMovie(trending: Trending)
    fun getTrendMovie(): LiveData<List<Trending>>
    suspend fun trendingMovieRows(): Int

    suspend fun insertUpcoming(upcoming: List<Upcoming>)
    suspend fun updateUpcoming(upcoming: Upcoming)
    fun getUpcomingMovie(): LiveData<List<Upcoming>>
    suspend fun upcomingRows(): Int

    suspend fun insertGenre(genre: List<Genre>)
    suspend fun updateGenre(genre: Genre)
    fun getPartOfGenre(): LiveData<List<Genre>>
    fun getAllGenre(): LiveData<List<Genre>>
    suspend fun genreRows(): Int

    suspend fun insertDiscoverTv(tv: List<Tv>)
    suspend fun updateDiscoverTv(tv: Tv)
    fun getDiscoverTv(): LiveData<List<Tv>>
    suspend fun tvRows(): Int

    suspend fun insertDiscoverMovie(movies: List<Movies>)
    suspend fun updateDiscoverMovie(movies: Movies)
    fun getAllDiscoverMovie(): LiveData<List<Movies>>
    suspend fun movieRows(): Int
}