package com.themovie.di

import com.themovie.di.detailmovie.DetailMovieComponent
import com.themovie.di.detailtv.DetailTvComponent
import com.themovie.di.main.MainComponent
import com.themovie.di.search.SearchComponent
import com.themovie.di.suggest.SuggestComponent
import dagger.Module

@Module(subcomponents = [
    MainComponent::class,
    DetailMovieComponent::class,
    DetailTvComponent::class,
    SearchComponent::class,
    SuggestComponent::class])
class SubComponentModule