package com.aldebaran.data.network

import com.aldebaran.data.BuildConfig
import com.aldebaran.data.network.service.ArtistServices
import com.aldebaran.data.network.service.MovieServices
import com.aldebaran.data.network.service.TvServices
import com.aldebaran.data.network.source.ArtistRemoteSourceImpl
import com.aldebaran.data.network.source.MovieRemoteSourceImpl
import com.aldebaran.data.network.source.TvRemoteSourceImpl
import com.aldebaran.data.repository.*
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.repository.local.*
import com.aldebaran.domain.repository.remote.ArtistRemoteSource
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import com.aldebaran.domain.repository.remote.TvRemoteSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
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