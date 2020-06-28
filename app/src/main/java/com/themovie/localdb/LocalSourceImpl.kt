package com.themovie.localdb

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.themovie.localdb.dao.*
import com.themovie.model.db.*

class LocalSourceImpl(
    private val trendingDao: TrendingDao, private val upcomingDao: UpcomingDao,
    private val genresDao: GenresDao, private val tvDao: TvDao,
    private val moviesDao: MoviesDao
): LocalSource {
    override suspend fun insertTrendMovie(trending: List<Trending>) {
        trendingDao.insertAll(*trending.toTypedArray())
    }

    override suspend fun updateTrendingMovie(trending: Trending) {
        trendingDao.update(trending)
    }

    override fun getTrendMovie(): LiveData<List<Trending>> {
        return trendingDao.getTrending().asLiveData()
    }

    override suspend fun trendingMovieRows(): Int {
        return trendingDao.countRows()
    }

    override suspend fun insertUpcoming(upcoming: List<Upcoming>) {
        upcomingDao.insertAll(*upcoming.toTypedArray())
    }

    override fun getUpcomingMovie(): LiveData<List<Upcoming>> {
        return upcomingDao.getUpcomingMovie().asLiveData()
    }

    override suspend fun updateUpcoming(upcoming: Upcoming) {
        upcomingDao.update(upcoming)
    }

    override suspend fun upcomingRows(): Int {
        return upcomingDao.countRows()
    }

    override suspend fun insertGenre(genre: List<Genre>) {
        genresDao.insertAll(*genre.toTypedArray())
    }

    override suspend fun updateGenre(genre: Genre) {
        genresDao.update(genre)
    }

    override fun getPartOfGenre(): LiveData<List<Genre>> {
        return genresDao.getPartOfGenre().asLiveData()
    }

    override fun getAllGenre(): LiveData<List<Genre>> {
        return genresDao.getAllGenre().asLiveData()
    }

    override suspend fun genreRows(): Int {
        return genresDao.countRows()
    }

    override suspend fun insertDiscoverTv(tv: List<Tv>) {
        tvDao.insertAll(*tv.toTypedArray())
    }

    override suspend fun updateDiscoverTv(tv: Tv) {
        tvDao.update(tv)
    }

    override fun getDiscoverTv(): LiveData<List<Tv>> {
        return tvDao.getDiscoverTv().asLiveData()
    }

    override suspend fun tvRows(): Int {
        return tvDao.countRows()
    }

    override suspend fun insertDiscoverMovie(movies: List<Movies>) {
        moviesDao.insertAll(*movies.toTypedArray())
    }

    override suspend fun updateDiscoverMovie(movies: Movies) {
        moviesDao.update(movies)
    }

    override fun getAllDiscoverMovie(): LiveData<List<Movies>> {
        return moviesDao.getDiscoverMovies().asLiveData()
    }

    override suspend fun movieRows(): Int {
        return moviesDao.countRows()
    }
}