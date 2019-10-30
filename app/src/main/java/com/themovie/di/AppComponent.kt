package com.themovie.di

import com.themovie.ui.detail.DetailMovieFragment
import com.themovie.ui.detail.DetailTvFragment
import com.themovie.ui.discover.*
import com.themovie.ui.main.HomeFragment
import com.themovie.ui.main.MainActivity
import com.themovie.ui.person.PersonFragment
import dagger.Component
import javax.inject.Singleton

@Component (modules = [AppModule::class, NetworkModule::class, RoomModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(detailMovieFragment: DetailMovieFragment)
    fun inject(detailTvFragment: DetailTvFragment)
    fun inject(upcomingFragment: UpcomingFragment)
    fun inject(moviesFragment: MoviesFragment)
    fun inject(tvFragment: TvFragment)
    fun inject(personFragment: PersonFragment)
}