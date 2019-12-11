package com.themovie.ui.main

import android.os.Bundle
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

    }
}
