package com.themovie.di

import android.app.Application
import androidx.room.Room
import com.themovie.localdb.TheMovieDatabase
import com.themovie.localdb.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule
    (val application: Application) {

    private val theMovieDatabase: TheMovieDatabase = Room.databaseBuilder(
        application, TheMovieDatabase::class.java, "TheMovieDatabase"
    ).build()

    @Provides
    @Singleton
    fun provideTheMovieDatabase(): TheMovieDatabase {
        return theMovieDatabase
    }

    @Provides
    @Singleton
    fun provideTrendingDao(): TrendingDao {
        return theMovieDatabase.trendingDao()
    }

    @Provides
    @Singleton
    fun provideUpcomingDao(): UpcomingDao {
        return theMovieDatabase.upcomingDao()
    }

    @Provides
    @Singleton
    fun provideTvDao(): TvDao {
        return  theMovieDatabase.discoverTv()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(): MoviesDao {
        return theMovieDatabase.discoverMovies()
    }

    @Provides
    @Singleton
    fun provideGenreDao(): GenresDao {
        return theMovieDatabase.genreDao()
    }
}