package com.themovie.di

import com.themovie.di.detail.DetailComponent
import com.themovie.di.main.MainComponent
import com.themovie.di.search.SearchComponent
import com.themovie.di.suggest.SuggestComponent
import dagger.Module

@Module(subcomponents = [
    MainComponent::class,
    DetailComponent::class,
    SearchComponent::class,
    SuggestComponent::class])
class SubComponentModule