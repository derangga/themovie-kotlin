package com.themovie.repos.local

import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.*
import com.themovie.model.db.*
import javax.inject.Inject

class LocalRepository
@Inject constructor(
    private val trendingDao: TrendingDao, private val upcomingDao: UpcomingDao,
    private val genresDao: GenresDao, private val tvDao: TvDao,
    private val moviesDao: MoviesDao
){

    /**
     * Trending Repos
     */
    suspend fun insert(trending: Trending){
        trendingDao.insert(trending)
    }

    suspend fun insertAllTrend(trending: List<Trending>){
        trendingDao.insertAll(*trending.toTypedArray())
    }

    suspend fun update(trending: Trending){
        trendingDao.update(trending)
    }

    suspend fun getTrendingRows(): Int = trendingDao.getSizeOfRows()
    fun getAllTrending(): LiveData<List<Trending>> = trendingDao.getAllTrendingTv()

    /**
     * Upcoming Repos
     */
    suspend fun insert(upcoming: Upcoming){
        upcomingDao.insert(upcoming)
    }

    suspend fun insertAllUpcoming(upcoing: List<Upcoming>){
        upcomingDao.insertAll(*upcoing.toTypedArray())
    }

    suspend fun update(upcoming: Upcoming){
        upcomingDao.update(upcoming)
    }

    suspend fun getUpcomingRows(): Int = upcomingDao.getSizeOfRows()
    fun getAllUpcoming(): LiveData<List<Upcoming>> = upcomingDao.getAllUpcomingMv()

    /**
     * Genre Repos
     */
    suspend fun insert(genre: Genre){
        genresDao.insert(genre)
    }

    suspend fun insertAllGenre(genre: List<Genre>){
        genresDao.insertAll(*genre.toTypedArray())
    }

    suspend fun update(genre: Genre){
        genresDao.update(genre)
    }

    suspend fun getGenreRows(): Int = genresDao.getSizeOfRows()
    fun getPartOfGenre(): LiveData<List<Genre>> = genresDao.getPartOfGenre()
    fun getAllGenre(): LiveData<List<Genre>> = genresDao.getAllGenre()

    /**
     * Discover Tv Repos
     */

    suspend fun insert(tv: Tv){
        tvDao.insert(tv)
    }

    suspend fun insertAllTv(tv: List<Tv>){
        tvDao.insertAll(*tv.toTypedArray())
    }

    suspend fun update(tv: Tv){
        tvDao.update(tv)
    }

    suspend fun getTvRows(): Int = tvDao.getSizeOfRows()
    fun getAllDiscoverTv(): LiveData<List<Tv>> = tvDao.getAllDiscoverTv()

    /**
     * Discover Movies Repos
     */
    suspend fun insert(movies: Movies){
        moviesDao.insert(movies)
    }

    suspend fun insertAllMovie(movies: List<Movies>){
        moviesDao.insertAll(*movies.toTypedArray())
    }

    suspend fun update(movies: Movies){
        moviesDao.update(movies)
    }

    suspend fun getMoviesRows(): Int = moviesDao.getSizeOfRows()
    fun getAllDiscoverMovie(): LiveData<List<Movies>> = moviesDao.getAllDiscoverMovies()

    /**
     * Update local database
     */

    suspend fun updateLocalData(trendingList: List<Trending>, upcomingList: List<Upcoming>,
                                        genreList: List<Genre>, tvList: List<Tv>, movieList: List<Movies>)
    {
        trendingList.forEachIndexed { index, trending ->
            val data = Trending(
                index + 1, trending.id, trending.title,
                trending.posterPath, trending.backdropPath,
                trending.overview, trending.voteAverage, trending.releaseDate
            )
            update(data)
        }

        upcomingList.forEachIndexed { index, upcoming ->
            val data = Upcoming(
                index + 1, upcoming.id, upcoming.title,
                upcoming.posterPath, upcoming.backdropPath,
                upcoming.overview, upcoming.voteAverage, upcoming.releaseDate
            )
            update(data)
        }

        genreList.forEachIndexed { index, genre ->
            val data = Genre(index + 1, genre.id, genre.name)
            update(data)
        }

        tvList.forEachIndexed { index, tv ->
            val data = Tv(
                index + 1, tv.id, tv.name,
                tv.voteAverage, tv.voteCount,
                tv.posterPath, tv.backdropPath, tv.overview
            )
            update(data)
        }

        movieList.forEachIndexed { index, movies ->
            val data = Movies(
                index + 1, movies.id,
                movies.title, movies.posterPath, movies.backdropPath,
                movies.overview, movies.voteAverage, movies.releaseDate
            )
            update(data)
        }
    }
}