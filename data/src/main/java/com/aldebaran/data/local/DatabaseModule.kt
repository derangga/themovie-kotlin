package com.aldebaran.data.local

import android.content.Context
import androidx.room.Room
import com.aldebaran.data.local.dao.*
import com.aldebaran.data.local.source.*
import com.aldebaran.domain.repository.local.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTheMovieDatabase(@ApplicationContext context: Context): TheMovieDatabase {
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

    @Provides
    @Singleton
    fun provideGenreLocalSource(genreDao: GenresDao): GenreLocalSource {
        return GenreLocalSourceImpl(genreDao)
    }

    @Provides
    @Singleton
    fun provideMovieLocalSource(movieDao: MoviesDao): MovieLocalSource {
        return MovieLocalSourceImpl(movieDao)
    }

    @Provides
    @Singleton
    fun provideTrendingLocalSource(trendingDao: TrendingDao): TrendingLocalSource {
        return TrendingLocalSourceImpl(trendingDao)
    }

    @Provides
    @Singleton
    fun provideUpcomingLocalSource(upcomingDao: UpcomingDao): UpcomingLocalSource {
        return UpcomingLocalSourceImpl(upcomingDao)
    }

    @Provides
    @Singleton
    fun provideTvLocalSource(tvDao: TvDao): TvLocalSource {
        return TvLocalSourceImpl(tvDao)
    }
}