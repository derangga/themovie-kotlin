package com.themovie.ui.detail

import android.os.Bundle
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.helper.ImageCache
import com.themovie.helper.ViewPagerFragment
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity() {

    private val tag: String = DetailActivity::class.java.simpleName

    override fun getView(): Int {
        return R.layout.activity_detail
    }

    override fun setOnMain(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        val urlImg = ApiUrl.IMG_BACK + getBundle()?.getString("image")
        initTabLayout(getBundle()?.getInt("id"))
        ImageCache.setImageViewUrl(this, urlImg, detail_img)
        supportActionBar?.title = getString(R.string.detail_title_1)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    private fun initTabLayout(filmId: Int?) {
        val bundle = Bundle()
        val viewPagerAdapter = ViewPagerFragment(supportFragmentManager)
        val detailFragment = DetailMovieFragment()
        val videoFragment = VideoFragment()

        bundle.putInt("filmId", filmId!!)
        detailFragment.arguments = bundle
        videoFragment.arguments = bundle

        viewPagerAdapter.addFragment(getString(R.string.fragment_title_1), detailFragment)
        viewPagerAdapter.addFragment(getString(R.string.fragment_title_2), videoFragment)
        detail_viewpager.adapter = viewPagerAdapter

        detail_tab.setupWithViewPager(detail_viewpager, true)
        viewPagerAdapter.notifyDataSetChanged()
    }
}
