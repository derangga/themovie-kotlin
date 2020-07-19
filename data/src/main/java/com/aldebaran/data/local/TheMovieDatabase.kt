package com.aldebaran.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aldebaran.data.local.dao.*
import com.aldebaran.domain.entities.local.*

@Database(entities = [
    TrendingEntity::class,
    UpcomingEntity::class,
    GenreEntity::class,
    TvEntity::class,
    MovieEntity::class], version = 2, exportSchema = false)
abstract class TheMovieDatabase : RoomDatabase() {

    abstract fun trendingDao(): TrendingDao
    abstract fun genreDao(): GenresDao
    abstract fun upcomingDao(): UpcomingDao
    abstract fun discoverMovies(): MoviesDao
    abstract fun discoverTv(): TvDao
}