package com.themovie.di

import android.content.Context
import androidx.room.Room
import com.themovie.localdb.TheMovieDatabase
import com.themovie.localdb.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideTheMovieDatabase(context: Context): TheMovieDatabase {
        return Room.databaseBuilder(
            context, TheMovieDatabase::class.java, "TheMovieDatabase"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideTrendingDao(theMovieDatabase: TheMovieDatabase): TrendingDao {
        return theMovieDatabase.trendingDao()
    }

    @Provides
    @Singleton
    fun provideUpcomingDao(theMovieDatabase: TheMovieDatabase): UpcomingDao {
        return theMovieDatabase.upcomingDao()
    }

    @Provides
    @Singleton
    fun provideTvDao(theMovieDatabase: TheMovieDatabase): TvDao {
        return  theMovieDatabase.discoverTv()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(theMovieDatabase: TheMovieDatabase): MoviesDao {
        return theMovieDatabase.discoverMovies()
    }

    @Provides
    @Singleton
    fun provideGenreDao(theMovieDatabase: TheMovieDatabase): GenresDao {
        return theMovieDatabase.genreDao()
    }
}