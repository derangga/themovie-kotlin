package com.themovie.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.ui.discover.MovieViewModel
import com.themovie.ui.discover.TvViewModel
import com.themovie.ui.discover.UpComingViewModel
import com.themovie.ui.genres.GenreViewModel
import com.themovie.ui.main.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @MainScope
    @Binds
    abstract fun bindViewModelFactory(mainViewModelFactory: MainViewModelFactory): ViewModelProvider.Factory

    @MainScope
    @Binds
    @IntoMap
    @MainViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @MainScope
    @Binds
    @IntoMap
    @MainViewModelKey(UpComingViewModel::class)
    abstract fun bindUpcomingViewModel(upcomingViewModel: UpComingViewModel): ViewModel

    @MainScope
    @Binds
    @IntoMap
    @MainViewModelKey(GenreViewModel::class)
    abstract fun bindGenreViewModel(genreViewModel: GenreViewModel): ViewModel

    @MainScope
    @Binds
    @IntoMap
    @MainViewModelKey(TvViewModel::class)
    abstract fun bindTvViewModel(tvViewModel: TvViewModel): ViewModel

    @MainScope
    @Binds
    @IntoMap
    @MainViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel
}