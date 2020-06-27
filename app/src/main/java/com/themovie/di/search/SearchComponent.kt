package com.themovie.di.search

import com.themovie.ui.search.SearchMovieFragment
import com.themovie.ui.search.SearchTvFragment
import dagger.Subcomponent

@SearchScope
@Subcomponent(modules = [SearchViewModelModule::class])
interface SearchComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): SearchComponent
    }

    fun inject(searchMovieFragment: SearchMovieFragment)
    fun inject(searchTvFragment: SearchTvFragment)
}