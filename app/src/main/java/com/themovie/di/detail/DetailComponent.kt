package com.themovie.di.detail


import com.themovie.ui.detail.DetailMovieFragment
import com.themovie.ui.detail.DetailTvFragment
import com.themovie.ui.person.PersonFragment
import dagger.Subcomponent

@DetailScope
@Subcomponent(modules = [DetailViewModelModule::class])
interface DetailComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): DetailComponent
    }

    fun inject(detailMovieFragment: DetailMovieFragment)
    fun inject(detailTvFragment: DetailTvFragment)
    fun inject(personFragment: PersonFragment)
}