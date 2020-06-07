package com.themovie.di.detailmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.di.detailtv.DetailTvScope
import com.themovie.di.detailtv.DetailTvViewModelKey
import com.themovie.ui.detail.viewmodel.DetailMovieViewModel
import com.themovie.ui.person.PersonViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailMovieViewModelModule {

    @DetailMovieScope
    @Binds
    abstract fun bindViewModelFactory(detailMovieViewModelFactory: DetailMovieViewModelFactory): ViewModelProvider.Factory

    @DetailMovieScope
    @Binds
    @IntoMap
    @DetailMovieViewModelKey(DetailMovieViewModel::class)
    abstract fun bindDetailMovieViewModel(detailMovieViewModel: DetailMovieViewModel): ViewModel

    @DetailMovieScope
    @Binds
    @IntoMap
    @DetailTvViewModelKey(PersonViewModel::class)
    abstract fun bindPersonViewModel(personViewModel: PersonViewModel): ViewModel
}