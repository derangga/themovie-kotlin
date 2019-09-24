package com.themovie.base.di

import com.themovie.ui.detail.DetailMovieFragment
import com.themovie.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component (modules = [AppModule::class, NetworkModule::class, RoomModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(detailMovieFragment: DetailMovieFragment)
}