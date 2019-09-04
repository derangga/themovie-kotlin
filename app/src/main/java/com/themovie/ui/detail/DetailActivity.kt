package com.themovie.ui.detail

import android.os.Bundle
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.helper.Constant
import com.themovie.helper.ImageCache
import com.themovie.helper.ViewPagerFragment
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity() {

    private val tag: String = DetailActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        val urlImg = ApiUrl.IMG_BACK + getBundle()?.getString("image")
        val detailFor = getBundle()?.getString("detail")
        initTabLayout(getBundle()?.getInt("id"), detailFor.toString())
        ImageCache.setImageViewUrl(this, urlImg, detail_img)
        supportActionBar?.title = getString(R.string.detail_title_1)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }



    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    private fun initTabLayout(filmId: Int?, detailFor: String) {
        val bundle = Bundle()
        val viewPagerAdapter = ViewPagerFragment(supportFragmentManager)
        val videoFragment = VideoFragment()
        bundle.putInt("filmId", filmId!!)

        if(detailFor.equals(Constant.MOVIE)){
            val detailMvFragment = DetailMovieFragment()
            bundle.putString("video", Constant.MOVIE)
            detailMvFragment.arguments = bundle
            viewPagerAdapter.addFragment(getString(R.string.fragment_title_1), detailMvFragment)
        }
        else {
            val detailTvFragment = DetailTvFragment()
            bundle.putString("video", Constant.TV)
            detailTvFragment.arguments = bundle
            viewPagerAdapter.addFragment(getString(R.string.fragment_title_1), detailTvFragment)
        }

        videoFragment.arguments = bundle
        viewPagerAdapter.addFragment(getString(R.string.fragment_title_2), videoFragment)
        detail_viewpager.adapter = viewPagerAdapter

        detail_tab.setupWithViewPager(detail_viewpager, true)
        viewPagerAdapter.notifyDataSetChanged()
    }
}
