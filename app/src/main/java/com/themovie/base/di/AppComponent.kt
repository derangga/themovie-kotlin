package com.themovie.base.di

import com.themovie.ui.detail.DetailMovieFragment
import com.themovie.ui.detail.DetailTvFragment
import com.themovie.ui.detail.VideoFragment
import com.themovie.ui.discover.DiscoverMovieActivity
import com.themovie.ui.discover.DiscoverTvActivity
import com.themovie.ui.main.MainActivity
import com.themovie.ui.person.PersonActivity
import dagger.Component
import javax.inject.Singleton

@Component (modules = [AppModule::class, NetworkModule::class, RoomModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(personActivity: PersonActivity)
    fun inject(discoverMovieActivity: DiscoverMovieActivity)
    fun inject(discoverTvActivity: DiscoverTvActivity)
    fun inject(detailMovieFragment: DetailMovieFragment)
    fun inject(detailTvFragment: DetailTvFragment)
    fun inject(videoFragment: VideoFragment)
}