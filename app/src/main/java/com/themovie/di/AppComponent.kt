package com.themovie.di

import android.app.Application
import com.themovie.di.detailmovie.DetailMovieComponent
import com.themovie.di.detailtv.DetailTvComponent
import com.themovie.di.main.MainComponent
import com.themovie.di.search.SearchComponent
import com.themovie.di.suggest.SuggestComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component (modules = [AppModule::class, NetworkModule::class, RoomModule::class, SubComponentModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun mainComponent(): MainComponent.Factory
    fun detailMovieComponent(): DetailMovieComponent.Factory
    fun detailTvComponent(): DetailTvComponent.Factory
    fun suggestComponent(): SuggestComponent.Factory
    fun searchComponent(): SearchComponent.Factory
}