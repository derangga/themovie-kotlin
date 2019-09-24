package com.themovie

import android.app.Application
import com.themovie.base.di.*

class MyApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this, this))
            .networkModule(NetworkModule())
            .roomModule(RoomModule(this)).build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}