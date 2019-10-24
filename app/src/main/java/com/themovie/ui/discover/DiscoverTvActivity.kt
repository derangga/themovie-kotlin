package com.themovie.ui.discover

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.themovie.MyApplication
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.helper.Constant
import com.themovie.model.online.discovertv.Tv
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.TvAdapter
import kotlinx.android.synthetic.main.activity_discover.*
import javax.inject.Inject

class DiscoverTvActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    @Inject lateinit var dcViewModelFactory: TvViewModelFactory
    private lateinit var tvAdapter: TvAdapter
    private lateinit var viewModel: TvViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)

        (application as MyApplication).getAppComponent().inject(this)
        viewModel = ViewModelProvider(this, dcViewModelFactory).get(TvViewModel::class.java)
        onItemHeaderClick()
        setupRecycler()
        dc_swipe.setOnRefreshListener(this)
    }

    override fun onStart() {
        super.onStart()
        getDiscoverTv()
        getLoadStatus()
    }

    override fun onStop() {
        super.onStop()
        unsubscribeData()
    }

    private fun onItemHeaderClick(){
//        h_back.setOnClickListener{
//            super.onBackPressed()
//        }
//        h_search.setOnClickListener {
//
//        }
    }

    private fun setupRecycler(){
        tvAdapter = TvAdapter()
        dc_recycler.apply {
            layoutManager = LinearLayoutManager(this@DiscoverTvActivity)
            adapter = tvAdapter
        }


        tvAdapter.setOnClickAdapter(object: TvAdapter.OnClickAdapterListener{
            override fun onItemClick(view: View?, tv: Tv, imageViewRes: ImageView) {
                val bundle = Bundle().apply {
                    putInt("id", tv.id)
                    putString("image", tv.backdropPath.toString())
                    putString("detail", Constant.TV)
                }
                changeActivityTransitionBundle(DetailActivity::class.java, bundle, imageViewRes)
            }
        })

        tvAdapter.setOnErrorClickListener(object: TvAdapter.OnErrorClickListener{
            override fun onClick(view: View?) {
                viewModel.retry()
            }
        })
    }

    private fun getDiscoverTv(){
        viewModel.getTvLiveData().observe( this,
            Observer<PagedList<Tv>> {
                tvAdapter.submitList(it)
                dc_swipe.isRefreshing = false
            }
        )
    }

    private fun unsubscribeData(){
        viewModel.stopSubscribing()
    }

    private fun getLoadStatus(){
        viewModel.getLoadState().observe( this,
            Observer{
                tvAdapter.setLoadState(it)
            }
        )
    }

    override fun onRefresh() {
        viewModel.refresh()
    }
}
