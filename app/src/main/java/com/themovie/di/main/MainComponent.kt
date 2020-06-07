package com.themovie.di.main

import com.themovie.ui.discover.MoviesFragment
import com.themovie.ui.discover.TvFragment
import com.themovie.ui.discover.UpcomingFragment
import com.themovie.ui.genres.GenresFragment
import com.themovie.ui.genres.MovieWithGenreFragment
import com.themovie.ui.main.HomeFragment
import dagger.Subcomponent

@MainScope
@Subcomponent(modules = [MainViewModelModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): MainComponent
    }

    fun inject(homeFragment: HomeFragment)
    fun inject(tvFragment: TvFragment)
    fun inject(moviesFragment: MoviesFragment)
    fun inject(upcomingFragment: UpcomingFragment)
    fun inject(genresFragment: GenresFragment)
    fun inject(movieWithGenreFragment: MovieWithGenreFragment)
}