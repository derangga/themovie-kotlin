package com.themovie

import com.themovie.di.AppComponent
import com.themovie.di.DaggerAppComponent
import com.themovie.di.main.MainComponent
import com.themovie.di.search.SearchComponent
import com.themovie.di.suggest.SuggestComponent

class UiTestApp: MyApplication() {

    private lateinit var component: AppComponent

    override fun getAppComponent(): AppComponent {
        component = DaggerAppComponent.builder()
            .application(this)
            .build()
        return component
    }

    override fun getMainComponent(): MainComponent? {
        return component.mainComponent().create()
    }

    override fun getSuggestComponent(): SuggestComponent? {
        return component.suggestComponent().create()
    }

    override fun getSearchComponent(): SearchComponent? {
        return component.searchComponent().create()
    }
}