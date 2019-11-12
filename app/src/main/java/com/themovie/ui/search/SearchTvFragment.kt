package com.themovie.ui.search


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.themovie.MyApplication

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentSearchResultBinding
import com.themovie.helper.Constant
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Tv
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.TvAdapter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SearchTvFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    @Inject lateinit var viewModelFactory: SearchTvFactory
    private var query: String? = ""
    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var viewModel: SearchTvViewModel
    private lateinit var mAdapter: TvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)
        binding.lifecycleOwner = this
        query = getBundle()?.getString("query")
        (activity?.application as MyApplication).getAppComponent().inject(this)
        SearchTvViewModel.query = query.orEmpty()
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchTvViewModel::class.java)

        return binding.root
    }

    override fun onMain(savedInstanceState: Bundle?) {
        binding.swipe.setOnRefreshListener(this)
        setupRecyclerView()
        getSearchResult()
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    private fun setupRecyclerView(){
        mAdapter = TvAdapter()
        mAdapter.apply {
            setOnClickAdapter(object: OnAdapterListener<Tv> {
                override fun onClick(view: View, item: Tv) {
                    val bundle = Bundle().apply {
                        putInt("filmId", item.id)
                        putString("type", Constant.TV)
                    }
                    changeActivity(bundle, DetailActivity::class.java)
                }
            })

            setOnErrorClickListener(object: TvAdapter.OnErrorClickListener{
                override fun onClick(view: View?) {
                    viewModel.retry()
                }
            })
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun getSearchResult(){
        viewModel.apply {
            getSearchTvResult().observe(this@SearchTvFragment,
                Observer<PagedList<Tv>>{
                    mAdapter.submitList(it)
                    binding.swipe.isRefreshing = false
                })

            getLoadState().observe(this@SearchTvFragment,
                Observer { mAdapter.setLoadState(it) })
        }


    }
}
