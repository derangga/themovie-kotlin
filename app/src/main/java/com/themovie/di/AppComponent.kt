package com.themovie.di

import com.themovie.ui.detail.DetailMovieFragment
import com.themovie.ui.detail.DetailTvFragment
import com.themovie.ui.detail.VideoFragment
import com.themovie.ui.discover.*
import com.themovie.ui.main.HomeFragment
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
    fun inject(homeFragment: HomeFragment)
    fun inject(detailMovieFragment: DetailMovieFragment)
    fun inject(detailTvFragment: DetailTvFragment)
    fun inject(videoFragment: VideoFragment)
    fun inject(upcomingFragment: UpcomingFragment)
    fun inject(moviesFragment: MoviesFragment)
    fun inject(tvFragment: TvFragment)
}