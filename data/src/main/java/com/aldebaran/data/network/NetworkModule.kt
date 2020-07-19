package com.aldebaran.data.network

import com.aldebaran.data.BuildConfig
import com.aldebaran.data.network.service.MovieServices
import com.aldebaran.data.network.service.TvServices
import com.aldebaran.data.network.source.MovieRemoteSourceImpl
import com.aldebaran.data.network.source.TvRemoteSourceImpl
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import com.aldebaran.domain.repository.remote.TvRemoteSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

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
    fun provideMovieRemoteSource(movieServices: MovieServices): MovieRemoteSource {
        return MovieRemoteSourceImpl(movieServices)
    }

    @Provides
    @Singleton
    fun provideTvRemoteSource(tvServices: TvServices): TvRemoteSource {
        return TvRemoteSourceImpl(tvServices)
    }
}