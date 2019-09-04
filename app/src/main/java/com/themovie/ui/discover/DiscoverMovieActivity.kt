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
import kotlinx.android.synthetic.main.activity_discover_movie.*

class DiscoverMovieActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_movie)
        supportActionBar?.title = "Discover Movies"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        setupRecycler()
        getDiscoverMovie()
        getLoadStatus()
        mv_swipe.setOnRefreshListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    private fun setupRecycler(){
        movieAdapter = MovieAdapter()
        mv_recycler.layoutManager = LinearLayoutManager(this)
        mv_recycler.adapter = movieAdapter

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
                viewModel.retry()
            }
        })
    }

    private fun getDiscoverMovie(){
        viewModel.getMovieLiveData().observe( this,
            Observer<PagedList<Movies>> {
                movieAdapter.submitList(it)
                mv_swipe.isRefreshing = false
            }
        )
    }

    private fun getLoadStatus(){
        viewModel.getLoadState().observe( this,
            Observer{
                movieAdapter.setLoadState(it)
            }
        )
    }
}
