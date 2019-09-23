package com.themovie

import android.app.Application
import com.themovie.base.di.AppComponent
import com.themovie.base.di.AppModule
import com.themovie.base.di.DaggerAppComponent
import com.themovie.base.di.NetworkModule

class MyApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().networkModule(NetworkModule()).appModule(AppModule(this, this)).build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}