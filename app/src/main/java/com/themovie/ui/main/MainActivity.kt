package com.themovie.ui.main

import android.os.Bundle
import com.aldebaran.core.BaseActivity
import com.themovie.R
import com.themovie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

    }
}
