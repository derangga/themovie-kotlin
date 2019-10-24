package com.themovie.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.themovie.localdb.dao.*
import com.themovie.model.local.*

@Database(entities = [
    Trending::class,
    Upcoming::class,
    GenreLocal::class,
    TvLocal::class,
    MoviesLocal::class], version = 2, exportSchema = false)
abstract class TheMovieDatabase : RoomDatabase() {

    abstract fun trendingDao(): TrendingDao
    abstract fun genreDao(): GenresDao
    abstract fun upcomingDao(): UpcomingDao
    abstract fun discoverMovies(): MoviesDao
    abstract fun discoverTv(): TvDao
}