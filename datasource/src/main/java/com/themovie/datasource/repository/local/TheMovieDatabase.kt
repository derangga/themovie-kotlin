package com.themovie.datasource.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.themovie.datasource.repository.local.dao.GenresDao
import com.themovie.datasource.repository.local.dao.TrendingDao
import com.themovie.datasource.repository.local.dao.UpcomingDao
import com.themovie.datasource.repository.local.dao.MoviesDao
import com.themovie.datasource.repository.local.dao.TvDao
import com.themovie.datasource.entities.local.GenreEntity
import com.themovie.datasource.entities.local.UpcomingEntity
import com.themovie.datasource.entities.local.TrendingEntity
import com.themovie.datasource.entities.local.MovieEntity
import com.themovie.datasource.entities.local.TvEntity

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