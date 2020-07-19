package com.aldebaran.data.local

import android.content.Context
import androidx.room.Room
import com.aldebaran.data.local.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

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