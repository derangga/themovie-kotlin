package com.themovie.tmdb.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.themovie.core.page.BaseActivity

import com.themovie.tmdb.R
import com.themovie.tmdb.databinding.ActivityDetailBinding
import com.themovie.tmdb.helper.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    override fun getLayout(): Int {
        return R.layout.activity_detail
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val filmId = getBundle()?.getInt("filmId")
        val type = getBundle()?.getString("type")

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment2) as? NavHostFragment
        val navController = navHostFragment?.navController

        navController?.let {
            if (type == Constant.MOVIE) {
                navController.setGraph(
                    R.navigation.detail_movie_navigation,
                    DetailMovieFragmentArgs(filmId ?: 0).toBundle()
                )
            } else if (type == Constant.TV) {
                navController.setGraph(
                    R.navigation.detail_tv_navigation,
                    DetailMovieFragmentArgs(filmId ?: 0).toBundle()
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}
