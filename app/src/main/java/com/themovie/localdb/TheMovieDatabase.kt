package com.themovie.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.themovie.localdb.dao.*
import com.themovie.model.db.*

@Database(entities = [
    Trending::class,
    Upcoming::class,
    Genre::class,
    Tv::class,
    Movies::class], version = 2, exportSchema = false)
abstract class TheMovieDatabase : RoomDatabase() {

    abstract fun trendingDao(): TrendingDao
    abstract fun genreDao(): GenresDao
    abstract fun upcomingDao(): UpcomingDao
    abstract fun discoverMovies(): MoviesDao
    abstract fun discoverTv(): TvDao
}