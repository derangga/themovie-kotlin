package com.themovie.ui.detail

import android.os.Bundle
import androidx.navigation.findNavController
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.databinding.ActivityDetailBinding
import com.themovie.di.detail.DetailComponent
import com.themovie.helper.Constant

class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    override fun getLayout(): Int {
        return R.layout.activity_detail
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val filmId = getBundle()?.getInt("filmId")
        val type = getBundle()?.getString("type")
        if(type == Constant.MOVIE){
            findNavController(R.id.fragment2).setGraph(R.navigation.detail_movie_navigation,
                DetailMovieFragmentArgs(filmId ?: 0).toBundle())
        } else if(type == Constant.TV){
            findNavController(R.id.fragment2).setGraph(R.navigation.detail_tv_navigation,
                DetailMovieFragmentArgs(filmId ?: 0).toBundle())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    fun getDetailComponent(): DetailComponent? {
        return getApp().getDetailComponent()
    }
}
