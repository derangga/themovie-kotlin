package com.themovie.di.suggest

import dagger.Subcomponent

@SuggestScope
@Subcomponent(modules = [SuggestViewModelModule::class])
interface SuggestComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): SuggestComponent
    }
}