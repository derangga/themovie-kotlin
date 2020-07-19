package com.themovie.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themovie.localdb.LocalSource
import com.themovie.model.db.*

class FakeLocalSource: LocalSource {

    private val observableTrending = MutableLiveData<List<Trending>>()
    private val observableUpcoming = MutableLiveData<List<Upcoming>>()
    private val observableGenre = MutableLiveData<List<Genre>>()
    private val observableTv = MutableLiveData<List<Tv>>()
    private val observableMovie = MutableLiveData<List<Movies>>()

    override suspend fun insertTrendMovie(trending: List<Trending>) {

    }

    override suspend fun updateTrendingMovie(trending: Trending) {

    }

    override fun getTrendMovie(): LiveData<List<Trending>> {
        observableTrending.value = emptyList()
        return observableTrending
    }

    override suspend fun trendingMovieRows(): Int {
        return 0
    }

    override suspend fun insertUpcoming(upcoming: List<Upcoming>) {

    }

    override suspend fun updateUpcoming(upcoming: Upcoming) {

    }

    override fun getUpcomingMovie(): LiveData<List<Upcoming>> {
        observableUpcoming.value = emptyList()
        return observableUpcoming
    }

    override suspend fun upcomingRows(): Int {
        return 0
    }

    override suspend fun insertGenre(genre: List<Genre>) {

    }

    override suspend fun updateGenre(genre: Genre) {

    }

    override fun getPartOfGenre(): LiveData<List<Genre>> {
        observableGenre.value = emptyList()
        return observableGenre
    }

    override fun getAllGenre(): LiveData<List<Genre>> {
        observableGenre.value = emptyList()
        return observableGenre
    }

    override suspend fun genreRows(): Int {
        return 0
    }

    override suspend fun insertDiscoverTv(tv: List<Tv>) {

    }

    override suspend fun updateDiscoverTv(tv: Tv) {

    }

    override fun getDiscoverTv(): LiveData<List<Tv>> {
        observableTv.value = emptyList()
        return observableTv
    }

    override suspend fun tvRows(): Int {
        return 0
    }

    override suspend fun insertDiscoverMovie(movies: List<Movies>) {

    }

    override suspend fun updateDiscoverMovie(movies: Movies) {

    }

    override fun getAllDiscoverMovie(): LiveData<List<Movies>> {
        observableMovie.value = emptyList()
        return observableMovie
    }

    override suspend fun movieRows(): Int {
        return 0
    }
}