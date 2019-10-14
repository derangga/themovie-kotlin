package com.themovie.ui.main

import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.themovie.MyApplication
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.base.di.AppComponent
import com.themovie.databinding.ActivityMainBinding
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.local.MoviesLocal
import com.themovie.model.local.Trending
import com.themovie.model.local.TvLocal
import com.themovie.model.local.Upcoming
import com.themovie.model.online.FetchMainData
import com.themovie.restapi.ApiUrl
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.DiscoverMovieActivity
import com.themovie.ui.discover.DiscoverTvActivity
import com.themovie.ui.main.adapter.DiscoverMvAdapter
import com.themovie.ui.main.adapter.DiscoverTvAdapter
import com.themovie.ui.main.adapter.TrendingAdapter
import com.themovie.ui.main.adapter.UpcomingAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    @Inject lateinit var mainViewModelFactory: MainViewModelFactory

    private var timer: Timer? = null
    private lateinit var mainViewModel: MainViewModel
    private var isUpcomingEmpty: Boolean = true
    private var isTrendingEmpty: Boolean = true
    private var isDiscoverTvEmpty: Boolean = true
    private var isDiscoverMvEmpty: Boolean = true
    private var isSliding: Boolean = false
    private var isFirstTouch: Boolean = true
    private lateinit var upcomingAdapter: UpcomingAdapter
    private lateinit var trendingAdapter: TrendingAdapter
    private lateinit var discoverTvAdapter: DiscoverTvAdapter
    private lateinit var discoverMvAdapter: DiscoverMvAdapter
    private lateinit var snackbar: Snackbar
    private lateinit var binding: ActivityMainBinding
    private var currentPosition: Int = 0
    private var sizeOfHeader = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).getAppComponent().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)
        snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.detail_title_11), Snackbar.LENGTH_INDEFINITE)

        upcomingListSetup()
        fetchData()
        showData()
        observeNetworkLoad()
        onClick()
        main_swipe.setOnRefreshListener(this)
    }

    override fun onResume() {
        isFirstTouch = true
        startSliding()
        super.onResume()
    }

    override fun onPause() {
        stopSliding()
        super.onPause()
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

        main_trend.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingAdapter
        }

        main_upcoming.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingAdapter
        }

        main_disctv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = discoverTvAdapter
        }


        main_discmv.apply {
           layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = discoverMvAdapter
        }

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(main_trend)
        indicator.attachToRecyclerView(main_trend, pagerSnapHelper)
        trendingAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)
    }

    private fun onClick(){

        trendingAdapter.setOnClickListener(object: TrendingAdapter.OnClickAdapterListener{
            override fun onClick(view: View?, trending: Trending, imageViewRes: ImageView) {
                val bundle = Bundle().apply {
                    putInt("id", trending.tvId)
                    putString("image", trending.backDropPath)
                    putString("detail", Constant.TV)
                }
                snackbar.dismiss()
                changeActivityTransitionBundle(DetailActivity::class.java, bundle, imageViewRes)
            }
        })

        trendingAdapter.setOnTouchListener(object: TrendingAdapter.OnTouchAdapterListener{
            override fun onTouchItem(view: View?, motionEvent: MotionEvent?) {
                if(motionEvent?.action == MotionEvent.ACTION_DOWN && isFirstTouch){
                    isFirstTouch = false
                    stopSliding()
                }
            }
        })

        upcomingAdapter.setOnClickListener(object: UpcomingAdapter.OnClickAdapterListener{
            override fun onClick(view: View?, upcoming: Upcoming, imageViewRes: ImageView) {
                val bundle = Bundle().apply {
                    putInt("id", upcoming.mvId)
                    putString("image", upcoming.backDropPath)
                    putString("detail", Constant.MOVIE)
                }
                snackbar.dismiss()
                changeActivityTransitionBundle(DetailActivity::class.java, bundle, imageViewRes)
            }
        })

        discoverTvAdapter.setOnClickListener(object: DiscoverTvAdapter.OnClickAdapterListener{
            override fun onClick(view: View?, tvLocal: TvLocal, imageViewRes: ImageView) {
                val bundle = Bundle().apply {
                    putInt("id", tvLocal.tvId)
                    putString("image", tvLocal.backDropPath)
                    putString("detail", Constant.TV)
                }
                snackbar.dismiss()
                changeActivityTransitionBundle(DetailActivity::class.java, bundle, imageViewRes)
            }
        })

        discoverMvAdapter.setOnClickListener(object: DiscoverMvAdapter.OnClickAdapterListener {
            override fun onClick(view: View?, moviesLocal: MoviesLocal, imageViewRes: ImageView) {
                val bundle = Bundle().apply {
                    putInt("id", moviesLocal.mvId)
                    putString("image", moviesLocal.backDropPath)
                    putString("detail", Constant.MOVIE)
                }
                snackbar.dismiss()
                changeActivityTransitionBundle(DetailActivity::class.java, bundle, imageViewRes)
            }
        })

        see_upco.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("fetch", Constant.UPCOMING)
            changeActivity(bundle, DiscoverMovieActivity::class.java)
        }

        see_discotv.setOnClickListener {
            changeActivity(DiscoverTvActivity::class.java)
        }

        see_discomv.setOnClickListener {
            val bundling = Bundle()
            bundling.putString("fetch", Constant.DISCOVER)
            changeActivity(bundling, DiscoverMovieActivity::class.java)
        }

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
                if(!isUpcomingEmpty)
                    upcomingAdapter.submitList(t)
            })

        mainViewModel.getDiscoverTvLocalData().observe(this,
            Observer<List<TvLocal>> { t ->
                isDiscoverTvEmpty = t!!.isEmpty()
                if(!isDiscoverTvEmpty){
                    discoverTvAdapter.submitList(t)
                    disctv_card.visibility = View.VISIBLE
                }
            })

        mainViewModel.getDiscoverMvLocalData().observe(this,
            Observer<List<MoviesLocal>> { t ->
                isDiscoverMvEmpty = t!!.isEmpty()
                if(!isDiscoverMvEmpty){
                    discoverMvAdapter.submitList(t)
                    discmv_card.visibility = View.VISIBLE
                }
            })
    }

    private fun fetchData(){
        mainViewModel.getDataRequest(ApiUrl.TOKEN).observe(this,
            Observer<FetchMainData> { t ->
                if(isTrendingEmpty)
                    mainViewModel.insertLocalTrending(t?.trending!!.results)
                else
                    mainViewModel.updateLocalTrending(t?.trending!!.results)

                if(isUpcomingEmpty)
                    mainViewModel.insertLocalUpcoming(t.upcomingResponse.results)
                else
                    mainViewModel.updateLocalUpComing(t.upcomingResponse.results)

                if(isDiscoverTvEmpty)
                    mainViewModel.insertLocalTv(t.tvResponse.results)
                else
                    mainViewModel.updateLocalTv(t.tvResponse.results)

                if(isDiscoverMvEmpty)
                    mainViewModel.insertLocalMovies(t.moviesResponse.movies)
                else
                    mainViewModel.updateLocalMovies(t.moviesResponse.movies)

                hideLoading()
            })
    }

    private fun observeNetworkLoad(){
        mainViewModel.getLoadDataStatus().observe(this,
            Observer<LoadDataState>{
                if(it == LoadDataState.ERROR) {
                    main_swipe.isRefreshing = false
                    val view = snackbar.view
                    view.setBackgroundColor(getColor(R.color.colorBlackTransparent))
                    snackbar.setAction(getString(R.string.detail_title_12), View.OnClickListener {
                        fetchData()
                        snackbar.dismiss()
                    }).show()
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
