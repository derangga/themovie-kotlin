package com.themovie

import android.app.Application
import com.themovie.di.AppComponent
import com.themovie.di.DaggerAppComponent
import com.themovie.di.detail.DetailComponent
import com.themovie.di.main.MainComponent
import com.themovie.di.search.SearchComponent
import com.themovie.di.suggest.SuggestComponent
import timber.log.Timber

open class MyApplication : Application() {

    private lateinit var appComponent: AppComponent
    private var mainComponent: MainComponent? = null
    private var searchComponent: SearchComponent? = null
    private var suggestComponent: SuggestComponent? = null

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    open fun getAppComponent(): AppComponent {
        if(!this::appComponent.isInitialized){
            appComponent = DaggerAppComponent.builder()
                .application(this)
                .build()
        }
        return appComponent
    }

    open fun getMainComponent(): MainComponent?{
        if(mainComponent == null){
            mainComponent = appComponent.mainComponent().create()
        }
        return mainComponent
    }

    fun releaseMainComponent(){
        mainComponent = null
    }

    open fun getSuggestComponent(): SuggestComponent? {
        if(suggestComponent == null){
            suggestComponent = appComponent.suggestComponent().create()
        }
        return suggestComponent
    }

    open fun releaseSuggestComponent(){
        suggestComponent = null
    }

    open fun getSearchComponent(): SearchComponent? {
        if(searchComponent == null){
            searchComponent = appComponent.searchComponent().create()
        }
        return searchComponent
    }

    open fun releaseSearchComponent() {
        searchComponent = null
    }
}