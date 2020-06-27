package com.themovie.di.main

import com.themovie.localdb.LocalSource
import com.themovie.localdb.LocalSourceImpl
import com.themovie.localdb.dao.*
import com.themovie.repos.fromapi.RemoteSource
import com.themovie.repos.fromapi.RemoteSourceImpl
import com.themovie.repos.fromapi.TheMovieRepository
import com.themovie.restapi.ApiInterface
import com.themovie.ui.main.adapter.*
import dagger.Module
import dagger.Provides

@Module
object MainModule {

    @Provides
    @MainScope
    fun provideLocalDataSource(
        trendingDao: TrendingDao, upcomingDao: UpcomingDao,
        genresDao: GenresDao, tvDao: TvDao, moviesDao: MoviesDao): LocalSource{
        return LocalSourceImpl(trendingDao, upcomingDao, genresDao, tvDao, moviesDao)
    }

    @Provides
    @MainScope
    fun provideUpcomingAdapter() = UpcomingAdapter()


    @Provides
    @MainScope
    fun provideTrendingAdapter() = TrendingAdapter()

    @Provides
    @MainScope
    fun provideGenreAdapter() = GenreAdapter()

    @Provides
    @MainScope
    fun provideDiscoverTvAdapter() = DiscoverTvAdapter()

    @Provides
    @MainScope
    fun provideDiscoverMovieAdapter() = DiscoverMvAdapter()
}