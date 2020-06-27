package com.themovie.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    fun provideAppContext(application: Application): Context{
        return application.applicationContext
    }
}