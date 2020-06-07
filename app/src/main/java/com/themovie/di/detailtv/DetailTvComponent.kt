package com.themovie.di.detailtv


import dagger.Subcomponent

@DetailTvScope
@Subcomponent(modules = [DetailTvViewModelModule::class])
interface DetailTvComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): DetailTvComponent
    }
}