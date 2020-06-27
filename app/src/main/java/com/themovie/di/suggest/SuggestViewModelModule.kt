package com.themovie.di.suggest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.ui.search.SuggestMoviesViewModel
import com.themovie.ui.search.SuggestTvViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class SuggestViewModelModule {

    @SuggestScope
    @Binds
    abstract fun bindViewModelFactory(suggestViewModelFactory: SuggestViewModelFactory): ViewModelProvider.Factory

    @SuggestScope
    @Binds
    @IntoMap
    @SuggestViewModelKey(SuggestMoviesViewModel::class)
    abstract fun bindSuggestMoviesViewModel(suggestMovieViewModel: SuggestMoviesViewModel): ViewModel

    @SuggestScope
    @Binds
    @IntoMap
    @SuggestViewModelKey(SuggestTvViewModel::class)
    abstract fun bindSuggestTvViewModel(suggestTvViewModel: SuggestTvViewModel): ViewModel
}