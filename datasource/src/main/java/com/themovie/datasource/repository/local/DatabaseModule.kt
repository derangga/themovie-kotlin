package com.themovie.datasource.repository.local

import android.content.Context
import androidx.room.Room
import com.themovie.datasource.repository.local.dao.TrendingDao
import com.themovie.datasource.repository.local.dao.UpcomingDao
import com.themovie.datasource.repository.local.dao.GenresDao
import com.themovie.datasource.repository.local.dao.MoviesDao
import com.themovie.datasource.repository.local.dao.TvDao
import com.themovie.datasource.repository.local.source.GenreLocalSourceImpl
import com.themovie.datasource.repository.local.source.MovieLocalSourceImpl
import com.themovie.datasource.repository.local.source.TrendingLocalSourceImpl
import com.themovie.datasource.repository.local.source.UpcomingLocalSourceImpl
import com.themovie.datasource.repository.local.source.TvLocalSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
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