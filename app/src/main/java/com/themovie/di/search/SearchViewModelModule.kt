package com.themovie.di.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.ui.search.SearchMoviesViewModel
import com.themovie.ui.search.SearchTvViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchViewModelModule {

    @SearchScope
    @Binds
    abstract fun bindViewModelFactory(searchViewModelFactory: SearchViewModelFactory): ViewModelProvider.Factory

    @SearchScope
    @Binds
    @IntoMap
    @SearchViewModelKey(SearchMoviesViewModel::class)
    abstract fun bindSearchMoviesViewModel(searchMoviesViewModel: SearchMoviesViewModel): ViewModel

    @SearchScope
    @Binds
    @IntoMap
    @SearchViewModelKey(SearchTvViewModel::class)
    abstract fun bindSearchTvViewModel(searchTvViewModel: SearchTvViewModel): ViewModel
}