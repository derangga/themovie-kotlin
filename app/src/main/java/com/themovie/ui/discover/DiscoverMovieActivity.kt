package com.themovie.ui.discover

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.helper.Constant
import com.themovie.model.online.discovermv.Movies
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_discover.*
import kotlinx.android.synthetic.main.header_layout.*

class DiscoverMovieActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var dcViewModel: MovieViewModel
    private lateinit var upViewModel: UpComingViewModel
    private lateinit var fetch: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)

        fetch = getBundle()?.getString("fetch").toString()

        if(fetch.equals(Constant.UPCOMING)){
            h_title.visibility = View.VISIBLE
            h_title_layout.visibility = View.GONE
            upViewModel = ViewModelProviders.of(this).get(UpComingViewModel::class.java)
        } else {
            h_title.visibility = View.GONE
            h_title_layout.visibility = View.VISIBLE
            dcViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        }

        onItemHeaderClick()
        setupRecycler()
        dc_swipe.setOnRefreshListener(this)
    }

    override fun onStart() {
        super.onStart()
        getDiscoverMovie()
        getLoadStatus()
    }

    override fun onStop() {
        super.onStop()

        if(fetch.equals(Constant.UPCOMING)){
            upViewModel.stopSubscribing()
        }

        else {
            dcViewModel.stopSubscribing()
        }
    }

    override fun onRefresh() {
        if(fetch.equals(Constant.UPCOMING)){
            upViewModel.refresh()
        } else dcViewModel.refresh()
    }

    private fun onItemHeaderClick(){
        h_back.setOnClickListener{
            super.onBackPressed()
        }
        
        h_title_layout.setOnClickListener {

        }
    }

    private fun setupRecycler(){
        movieAdapter = MovieAdapter()
        dc_recycler.layoutManager = LinearLayoutManager(this)
        dc_recycler.adapter = movieAdapter

        movieAdapter.setOnClickAdapter(object: MovieAdapter.OnClickAdapterListener{
            override fun onItemClick(view: View?, movies: Movies, imageViewRes: ImageView) {
                val bundle= Bundle()
                bundle.putInt("id", movies.id)
                bundle.putString("image", movies.backdropPath.toString())
                bundle.putString("detail", Constant.MOVIE)
                changeActivityTransitionBundle(DetailActivity::class.java, bundle, imageViewRes)
            }
        })

        movieAdapter.setOnErrorClickListener(object: MovieAdapter.OnErrorClickListener{
            override fun onClick(view: View?) {
                if(fetch.equals(Constant.UPCOMING)){
                    upViewModel.retry()
                } else dcViewModel.retry()
            }
        })
    }

    private fun getDiscoverMovie(){

        if(fetch.equals(Constant.UPCOMING)){
            upViewModel.getUpcomingData().observe( this,
                Observer<PagedList<Movies>> {
                    movieAdapter.submitList(it)
                    dc_swipe.isRefreshing = false
                }
            )
        }

        else {
            dcViewModel.getMovieLiveData().observe( this,
                Observer<PagedList<Movies>> {
                    movieAdapter.submitList(it)
                    dc_swipe.isRefreshing = false
                }
            )
        }
    }

    private fun getLoadStatus(){
        if(fetch.equals(Constant.UPCOMING)){
            upViewModel.getLoadState().observe( this,
                Observer {
                    movieAdapter.setLoadState(it)
                }
            )
        }

        else {
            dcViewModel.getLoadState().observe( this,
                Observer{
                    movieAdapter.setLoadState(it)
                }
            )
        }

    }
}
