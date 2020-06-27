package com.themovie.di.suggest

import com.themovie.ui.search.SuggestMovieFragment
import com.themovie.ui.search.SuggestTvFragment
import dagger.Subcomponent

@SuggestScope
@Subcomponent(modules = [SuggestViewModelModule::class])
interface SuggestComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): SuggestComponent
    }

    fun inject(suggestMovieFragment: SuggestMovieFragment)
    fun inject(suggestTvFragment: SuggestTvFragment)
}