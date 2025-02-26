package com.themovie.tmdb.ui.search

import android.os.Bundle
import android.view.View
import com.themovie.core.page.BaseActivity
import com.themovie.core.utils.changeActivity
import com.themovie.tmdb.R
import com.themovie.tmdb.databinding.ActivitySearchBinding
import com.themovie.tmdb.helper.ViewPagerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    override fun getLayout(): Int {
        return R.layout.activity_search
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        initTab()

        binding.apply {
            hSearch.setOnClickListener { changeActivity<SuggestActivity>() }
            hBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun initTab(){
        val query = getBundle()?.getString("query")

        binding.apply {
            tabLayout.apply {
                visibility = View.VISIBLE
                addTab(this.newTab().setText("Movies"))
                addTab(this.newTab().setText("Tv"))
            }
            val pagerAdapter = ViewPagerFragment(supportFragmentManager)
            val searchMovie = SearchMovieFragment()
            val searchTv = SearchTvFragment()

            searchMovie.arguments = Bundle().apply { putString("query", query) }
            searchTv.arguments = Bundle().apply { putString("query", query) }

            pagerAdapter.apply {
                addFragment("Movies", searchMovie)
                addFragment("Tv", searchTv)
            }

            viewPager.apply {
                visibility = View.VISIBLE
                adapter = pagerAdapter
                offscreenPageLimit = 2
            }
            tabLayout.setupWithViewPager(viewPager)
            hSearch.apply { isFocusable = false; setText(query) }
            pagerAdapter.notifyDataSetChanged()
        }
    }
}
