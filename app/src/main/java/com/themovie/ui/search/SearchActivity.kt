package com.themovie.ui.search

import android.annotation.TargetApi
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.databinding.ActivitySearchBinding
import com.themovie.helper.ViewPagerFragment

class SearchActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchBinding
    private var query: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        query = getBundle()?.getString("query")
        initTab()

        binding.apply {
            hSearch.setOnClickListener { changeActivity(SuggestActivity::class.java) }
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
