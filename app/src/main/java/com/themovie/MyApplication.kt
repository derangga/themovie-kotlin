package com.themovie

import android.app.Application
import com.themovie.di.AppComponent
import com.themovie.di.DaggerAppComponent
import com.themovie.di.detail.DetailComponent
import com.themovie.di.main.MainComponent
import com.themovie.di.search.SearchComponent
import com.themovie.di.suggest.SuggestComponent
import timber.log.Timber

class MyApplication : Application() {

    private lateinit var appComponent: AppComponent
    private var mainComponent: MainComponent? = null
    private var searchComponent: SearchComponent? = null
    private var suggestComponent: SuggestComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }

    fun getMainComponent(): MainComponent?{
        if(mainComponent == null){
            mainComponent = appComponent.mainComponent().create()
        }
        return mainComponent
    }

    fun releaseMainComponent(){
        mainComponent = null
    }

    fun getSuggestComponent(): SuggestComponent? {
        if(suggestComponent == null){
            suggestComponent = appComponent.suggestComponent().create()
        }
        return suggestComponent
    }

    fun releaseSuggestComponent(){
        suggestComponent = null
    }

    fun getSearchComponent(): SearchComponent? {
        if(searchComponent == null){
            searchComponent = appComponent.searchComponent().create()
        }
        return searchComponent
    }

    fun releaseSearchComponent() {
        searchComponent = null
    }
}