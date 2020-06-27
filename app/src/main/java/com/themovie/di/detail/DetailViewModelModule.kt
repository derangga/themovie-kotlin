package com.themovie.di.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.ui.detail.viewmodel.DetailMovieViewModel
import com.themovie.ui.detail.viewmodel.DetailTvViewModel
import com.themovie.ui.person.PersonViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(detailViewModelFactory: DetailViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @DetailViewModelKey(DetailMovieViewModel::class)
    abstract fun bindDetailMovieViewModel(detailMovieViewModel: DetailMovieViewModel): ViewModel

    @Binds
    @IntoMap
    @DetailViewModelKey(PersonViewModel::class)
    abstract fun bindPersonViewModel(personViewModel: PersonViewModel): ViewModel

    @Binds
    @IntoMap
    @DetailViewModelKey(DetailTvViewModel::class)
    abstract fun bindDetailTvViewModel(detailTvViewModel: DetailTvViewModel): ViewModel
}