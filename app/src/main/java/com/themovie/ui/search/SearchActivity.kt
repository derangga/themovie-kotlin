package com.themovie.ui.search

import android.os.Bundle
import android.view.View
import com.aldebaran.base.BaseActivity
import com.aldebaran.utils.changeActivity
import com.themovie.R
import com.themovie.databinding.ActivitySearchBinding
import com.themovie.helper.ViewPagerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    private var query: String? = ""

    override fun getLayout(): Int {
        return R.layout.activity_search
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        query = getBundle()?.getString("query")
        initTab()

        binding.apply {
            hSearch.setOnClickListener { changeActivity<SuggestActivity>() }
            hBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun initTab(){
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
