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
    private var detailComponent: DetailComponent? = null
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

    fun getDetailComponent(): DetailComponent? {
        if(detailComponent == null){
            detailComponent = appComponent.detailMovieComponent().create()
        }
        return detailComponent
    }

    fun releaseDetailComponent(){
        detailComponent = null
    }
}