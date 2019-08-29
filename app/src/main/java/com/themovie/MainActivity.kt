package com.themovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovie.base.BaseActivity
import com.themovie.model.local.Upcoming
import com.themovie.model.online.MainData
import com.themovie.restapi.ApiUrl
import com.themovie.ui.MainViewModel
import com.themovie.ui.UpcomingAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val tag: String = MainActivity::class.java.simpleName
    private lateinit var mainViewModel: MainViewModel
    private var isLocalDataEmpty: Boolean = true
    private lateinit var upcomingAdapter: UpcomingAdapter
    override fun getView(): Int {
        return R.layout.activity_main
    }

    override fun setOnMain(savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        upcomingListSetup()
        showUpcomingMovies()
    }

    private fun upcomingListSetup(){
        upcomingAdapter = UpcomingAdapter()
        upcoming_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        upcoming_list.adapter = upcomingAdapter
    }

    private fun showUpcomingMovies(){
        mainViewModel.getUpcomingLocalData().observe(this, object: Observer<List<Upcoming>>{
            override fun onChanged(t: List<Upcoming>?) {
                isLocalDataEmpty = t!!.isEmpty()
                upcomingAdapter.submitList(t)
                loading.visibility = View.INVISIBLE
            }
        })

        mainViewModel.getDataRequest(ApiUrl.TOKEN).observe(this, object: Observer<MainData>{
            override fun onChanged(t: MainData?) {
                if(isLocalDataEmpty){
                    mainViewModel.insertLocalUpcoming(t?.upcomingResponse!!.results)
                }else{
                    mainViewModel.updateLocalUpComing(t?.upcomingResponse!!.results)
                }
            }
        })
    }

}
