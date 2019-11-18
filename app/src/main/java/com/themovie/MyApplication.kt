package com.themovie

import android.app.Application
import com.themovie.di.*
import com.themovie.di.AppComponent
import com.themovie.di.AppModule
import com.themovie.di.NetworkModule
import com.themovie.di.RoomModule

class MyApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this, this))
            .networkModule(NetworkModule())
            .roomModule(RoomModule()).build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}