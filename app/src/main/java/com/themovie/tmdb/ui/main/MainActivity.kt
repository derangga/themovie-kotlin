package com.themovie.tmdb.ui.main

import android.os.Bundle
import com.themovie.core.page.BaseActivity
import com.themovie.tmdb.R
import com.themovie.tmdb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

    }
}
