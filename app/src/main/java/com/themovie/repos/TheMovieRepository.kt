package com.themovie.repos

import com.themovie.di.main.MainScope
import com.themovie.helper.resultLiveData
import com.themovie.localdb.LocalSource
import com.themovie.model.db.*
import javax.inject.Inject

@MainScope
class TheMovieRepository @Inject constructor(
    private val local: LocalSource,
    private val remote: RemoteSource
) {
    fun observeTrending() = resultLiveData(
        databaseQuery = { local.getTrendMovie() },
        networkCall = { remote.getPopularMovie(1) },
        saveCallResult = { res ->
            val rows = local.trendingMovieRows()
            if(rows == 0) local.insertTrendMovie(res.results)
            else {
                res.results.forEachIndexed { key, trending ->
                    val data = Trending(
                        key + 1, trending.id, trending.title,
                        trending.posterPath, trending.backdropPath,
                        trending.overview, trending.voteAverage, trending.releaseDate
                    )
                    local.updateTrendingMovie(data)
                }
            }
        }
    )

    fun observeUpcoming() = resultLiveData(
        databaseQuery = { local.getUpcomingMovie() },
        networkCall = { remote.getUpcomingMovie(1) },
        saveCallResult = { res ->
            val rows = local.upcomingRows()
            if(rows == 0) local.insertUpcoming(res.results)
            else {
                res.results.forEachIndexed { key, upcoming ->
                    val data = Upcoming(
                        key + 1, upcoming.id, upcoming.title,
                        upcoming.posterPath, upcoming.backdropPath,
                        upcoming.overview, upcoming.voteAverage, upcoming.releaseDate
                    )
                    local.updateUpcoming(data)
                }
            }
        }
    )

    fun observeGenre() = resultLiveData(
        databaseQuery = { local.getPartOfGenre() },
        networkCall = { remote.getGenreMovie() },
        saveCallResult = { res ->
            val rows = local.genreRows()
            if(rows == 0) local.insertGenre(res.genres)
            else {
                res.genres.forEachIndexed { key, genre ->
                    val data = Genre(key + 1, genre.id, genre.name)
                    local.updateGenre(data)
                }
            }
        }
    )

    fun observeDiscoverTv() = resultLiveData(
        databaseQuery = { local.getDiscoverTv() },
        networkCall = { remote.getDiscoverTv(1) },
        saveCallResult = { res ->
            val rows = local.tvRows()
            if(rows == 0) local.insertDiscoverTv(res.results)
            else {
                res.results.forEachIndexed { key, tv ->
                    val data = Tv(
                        key + 1, tv.id, tv.name,
                        tv.voteAverage, tv.voteCount,
                        tv.posterPath, tv.backdropPath, tv.overview
                    )
                    local.updateDiscoverTv(data)
                }
            }
        }
    )

    fun observeDiscoverMovie() = resultLiveData(
        databaseQuery = { local.getAllDiscoverMovie() },
        networkCall = { remote.getDiscoverMovie(1) },
        saveCallResult = { res ->
            val rows = local.movieRows()
            if(rows == 0) local.insertDiscoverMovie(res.movies)
            else {
                res.movies.forEachIndexed { key, movies ->
                    val data = Movies(
                        key + 1, movies.id,
                        movies.title, movies.posterPath, movies.backdropPath,
                        movies.overview, movies.voteAverage, movies.releaseDate
                    )
                    local.updateDiscoverMovie(data)
                }
            }
        }
    )

}