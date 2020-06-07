package com.themovie.di.detailtv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.ui.detail.viewmodel.DetailTvViewModel
import com.themovie.ui.person.PersonViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailTvViewModelModule {

    @DetailTvScope
    @Binds
    abstract fun bindViewModelFactory(detailTvViewModelFactory: DetailTvViewModelFactory): ViewModelProvider.Factory

    @DetailTvScope
    @Binds
    @IntoMap
    @DetailTvViewModelKey(DetailTvViewModel::class)
    abstract fun bindDetailTvViewModel(detailTvViewModel: DetailTvViewModel): ViewModel

    @DetailTvScope
    @Binds
    @IntoMap
    @DetailTvViewModelKey(PersonViewModel::class)
    abstract fun bindPersonViewModel(personViewModel: PersonViewModel): ViewModel
}