package com.themovie.ui.main

import android.os.Bundle
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.databinding.ActivityMainBinding
import com.themovie.di.main.MainComponent

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

    }

    fun getMainComponent(): MainComponent?{
        return getApp().getMainComponent()
    }
}
