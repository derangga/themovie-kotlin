package com.themovie.di.detailmovie


import dagger.Subcomponent

@DetailMovieScope
@Subcomponent(modules = [DetailMovieViewModelModule::class])
interface DetailMovieComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): DetailMovieComponent
    }


}