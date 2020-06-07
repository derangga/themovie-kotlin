package com.themovie.di.search

import dagger.Subcomponent

@SearchScope
@Subcomponent(modules = [SearchViewModelModule::class])
interface SearchComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): SearchComponent
    }
}