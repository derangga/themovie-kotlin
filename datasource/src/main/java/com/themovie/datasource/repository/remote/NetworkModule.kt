package com.themovie.datasource.repository.remote

import com.themovie.datasource.BuildConfig
import com.themovie.datasource.repository.remote.service.ArtistServices
import com.themovie.datasource.repository.remote.service.MovieServices
import com.themovie.datasource.repository.remote.service.TvServices
import com.themovie.datasource.repository.remote.source.ArtistRemoteSourceImpl
import com.themovie.datasource.repository.remote.source.MovieRemoteSourceImpl
import com.themovie.datasource.repository.remote.source.TvRemoteSourceImpl
import com.themovie.datasource.repository.sot.MovieRepository
import com.themovie.datasource.repository.Repository
import com.themovie.datasource.repository.local.GenreLocalSource
import com.themovie.datasource.repository.local.MovieLocalSource
import com.themovie.datasource.repository.local.UpcomingLocalSource
import com.themovie.datasource.repository.local.TrendingLocalSource
import com.themovie.datasource.repository.local.TvLocalSource
import com.themovie.datasource.repository.sot.GenreRepository
import com.themovie.datasource.repository.sot.TrendingRepository
import com.themovie.datasource.repository.sot.TvRepository
import com.themovie.datasource.repository.sot.UpcomingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideRequestHeader(): OkHttpClient {
        val httpLoginInterceptor = HttpLoggingInterceptor()
        httpLoginInterceptor.level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(httpLoginInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_GATEWAY)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieServices(retrofit: Retrofit): MovieServices {
        return retrofit.create(MovieServices::class.java)
    }

    @Provides
    @Singleton
    fun provideTvServices(retrofit: Retrofit): TvServices {
        return retrofit.create(TvServices::class.java)
    }

    @Provides
    @Singleton
    fun provideArtistServices(retrofit: Retrofit): ArtistServices {
        return retrofit.create(ArtistServices::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRemoteSource(movieServices: MovieServices): MovieRemoteSource {
        return MovieRemoteSourceImpl(movieServices)
    }

    @Provides
    @Singleton
    fun provideTvRemoteSource(tvServices: TvServices): TvRemoteSource {
        return TvRemoteSourceImpl(tvServices)
    }

    @Provides
    @Singleton
    fun provideArtistRemoteSource(artistServices: ArtistServices): ArtistRemoteSource {
        return ArtistRemoteSourceImpl(artistServices)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(local: MovieLocalSource, remote: MovieRemoteSource): Repository.MovieRepos {
        return MovieRepository(local, remote)
    }

    @Provides
    @Singleton
    fun provideTrendingRepository(local: TrendingLocalSource, remote: MovieRemoteSource): Repository.TrendingRepos {
        return TrendingRepository(local, remote)
    }

    @Provides
    @Singleton
    fun provideTvRepository(local: TvLocalSource, remote: TvRemoteSource): Repository.TvRepos {
        return TvRepository(local, remote)
    }

    @Provides
    @Singleton
    fun provideUpcomingRepository(local: UpcomingLocalSource, remote: MovieRemoteSource): Repository.UpcomingRepos {
        return UpcomingRepository(local, remote)
    }

    @Provides
    @Singleton
    fun provideGenreRepository(local: GenreLocalSource, remote: MovieRemoteSource): Repository.GenreRepos {
        return GenreRepository(local, remote)
    }
}