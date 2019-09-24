package com.themovie.base.di

import android.app.Application
import androidx.room.Room
import com.themovie.localdb.TheMovieDatabase
import com.themovie.localdb.dao.MoviesDao
import com.themovie.localdb.dao.TrendingDao
import com.themovie.localdb.dao.TvDao
import com.themovie.localdb.dao.UpcomingDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(val application: Application) {

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
}