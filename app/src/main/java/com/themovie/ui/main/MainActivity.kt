package com.themovie.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.helper.LoadDataState
import com.themovie.model.local.MoviesLocal
import com.themovie.model.local.Trending
import com.themovie.model.local.TvLocal
import com.themovie.model.local.Upcoming
import com.themovie.model.online.MainData
import com.themovie.restapi.ApiUrl
import com.themovie.ui.main.adapter.DiscoverMvAdapter
import com.themovie.ui.main.adapter.DiscoverTvAdapter
import com.themovie.ui.main.adapter.TrendingAdapter
import com.themovie.ui.main.adapter.UpcomingAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private var timer: Timer? = null
    private lateinit var mainViewModel: MainViewModel
    private var isUpcomingEmpty: Boolean = true
    private var isTrendingEmpty: Boolean = true
    private var isDiscoverTvEmpty: Boolean = true
    private var isDiscoverMvEmpty: Boolean = true
    private var isSliding: Boolean = false
    private lateinit var upcomingAdapter: UpcomingAdapter
    private lateinit var trendingAdapter: TrendingAdapter
    private lateinit var discoverTvAdapter: DiscoverTvAdapter
    private lateinit var discoverMvAdapter: DiscoverMvAdapter
    private var currentPosition: Int = 0
    private var sizeOfHeader = 0

    override fun getView(): Int {
        return R.layout.activity_main
    }

    override fun setOnMain(savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        upcomingListSetup()
        fetchData()
        showData()
        observeNetworkLoad()
        main_swipe.setOnRefreshListener(this)
    }

    override fun onResume() {
        super.onResume()
        startSliding()
    }

    override fun onPause() {
        super.onPause()
        stopSliding()
    }

    override fun onRefresh() {
        stopSliding()
        fetchData()
    }

    private fun upcomingListSetup(){
        upcomingAdapter = UpcomingAdapter()
        trendingAdapter = TrendingAdapter()
        discoverTvAdapter = DiscoverTvAdapter()
        discoverMvAdapter = DiscoverMvAdapter()

        main_trend.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        main_upcoming.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        main_disctv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        main_discmv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        main_trend.adapter = trendingAdapter
        main_upcoming.adapter = upcomingAdapter
        main_disctv.adapter = discoverTvAdapter
        main_discmv.adapter = discoverMvAdapter

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(main_trend)
        indicator.attachToRecyclerView(main_trend, pagerSnapHelper)
        trendingAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)
    }

    private fun showData(){

        mainViewModel.getTrendingLocalData().observe(this,
            Observer<List<Trending>>{ t ->
                isTrendingEmpty = t!!.isEmpty()
                if(!isTrendingEmpty){
                    hideLoading()
                    sizeOfHeader = t.size
                    trendingAdapter.submitList(t)
                }
            })

        mainViewModel.getUpcomingLocalData().observe(this,
            Observer<List<Upcoming>> { t ->
                isUpcomingEmpty = t!!.isEmpty()
                upcomingAdapter.submitList(t)
            })

        mainViewModel.getDiscoverTvLocalData().observe(this,
            Observer<List<TvLocal>> { t ->
                isDiscoverTvEmpty = t!!.isEmpty()
                discoverTvAdapter.submitList(t)
                disctv_card.visibility = View.VISIBLE
            })

        mainViewModel.getDiscoverMvLocalData().observe(this,
            Observer<List<MoviesLocal>> { t ->
                isDiscoverMvEmpty = t!!.isEmpty()
                discoverMvAdapter.submitList(t)
                discmv_card.visibility = View.VISIBLE
            })
    }

    private fun fetchData(){
        mainViewModel.getDataRequest(ApiUrl.TOKEN).observe(this, object: Observer<MainData>{
            override fun onChanged(t: MainData?) {
                if(isTrendingEmpty || isUpcomingEmpty || isDiscoverTvEmpty || isDiscoverMvEmpty){
                    mainViewModel.insertLocalUpcoming(t?.upcomingResponse!!.results)
                    mainViewModel.insertLocalTrending(t.trending.results)
                    mainViewModel.insertLocalTv(t.tvResponse.results)
                    mainViewModel.insertLocalMovies(t.moviesResponse.movies)
                    hideLoading()
                }else{
                    mainViewModel.updateLocalUpComing(t?.upcomingResponse!!.results)
                    mainViewModel.updateLocalTrending(t.trending.results)
                    mainViewModel.updateLocalTv(t.tvResponse.results)
                    mainViewModel.updateLocalMovies(t.moviesResponse.movies)
                }
            }
        })
    }

    fun observeNetworkLoad(){
        mainViewModel.getLoadDataStatus().observe(this,
            Observer<LoadDataState>{
                if(it == LoadDataState.ERROR) {
                    showToastMessage("Please check your internet connection")
                    main_swipe.isRefreshing = false
                }
            })
    }

    private fun hideLoading(){
        main_swipe.isRefreshing = false
        shimmerTrending.visibility = View.GONE
        shimmerupcoming.visibility = View.GONE
    }

    private fun startSliding() {
        if(!isSliding){
            isSliding = true
            timer = Timer()
            timer?.scheduleAtFixedRate(SliderTimer(), 5000, 5000)
        }
    }

    private fun stopSliding() {
        if(isSliding){
            isSliding = false
            timer?.cancel()
            timer = null
        }
    }


    inner class SliderTimer : TimerTask() {
        override fun run() {
            runOnUiThread {
                if(currentPosition < sizeOfHeader - 1){
                    currentPosition++
                    main_trend.post {
                        main_trend.smoothScrollToPosition(currentPosition)
                    }
                } else {
                    currentPosition = 0
                    main_trend.post {
                        main_trend.smoothScrollToPosition(currentPosition)
                    }
                }
            }
        }
    }

}
