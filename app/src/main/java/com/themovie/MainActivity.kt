package com.themovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovie.base.BaseActivity
import com.themovie.model.local.Trending
import com.themovie.model.local.Upcoming
import com.themovie.model.online.MainData
import com.themovie.restapi.ApiUrl
import com.themovie.ui.MainViewModel
import com.themovie.ui.TrendingAdapter
import com.themovie.ui.UpcomingAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val tag: String = MainActivity::class.java.simpleName
    private lateinit var mainViewModel: MainViewModel
    private var isLocalUpcomingEmpty: Boolean = true
    private var isLocalTrendingEmpty: Boolean = true
    private lateinit var upcomingAdapter: UpcomingAdapter
    private lateinit var trendingAdapter: TrendingAdapter
    override fun getView(): Int {
        return R.layout.activity_main
    }

    override fun setOnMain(savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        upcomingListSetup()
        showData()
    }

    private fun upcomingListSetup(){
        upcomingAdapter = UpcomingAdapter()
        trendingAdapter = TrendingAdapter()
        upcoming_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        trending_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        upcoming_list.adapter = upcomingAdapter
        trending_list.adapter = trendingAdapter
    }

    private fun showData(){
        mainViewModel.getUpcomingLocalData().observe(this,
            Observer<List<Upcoming>> { t ->
                isLocalUpcomingEmpty = t!!.isEmpty()
                upcomingAdapter.submitList(t)
                loading_1.visibility = View.INVISIBLE
            })

        mainViewModel.getTrendingLocalData().observe(this,
            Observer<List<Trending>>{ t ->
                isLocalTrendingEmpty = t!!.isEmpty()
                trendingAdapter.submitList(t)
                loading_2.visibility = View.INVISIBLE
        })

        mainViewModel.getDataRequest(ApiUrl.TOKEN).observe(this, object: Observer<MainData>{
            override fun onChanged(t: MainData?) {
                if(isLocalUpcomingEmpty || isLocalTrendingEmpty){
                    mainViewModel.insertLocalUpcoming(t?.upcomingResponse!!.results)
                    mainViewModel.insertLocalTrending(t.tvResponse.results)
                }else{
                    mainViewModel.updateLocalUpComing(t?.upcomingResponse!!.results)
                    mainViewModel.updateLocalTrending(t.tvResponse.results)
                }
            }
        })
    }

}
