package com.themovie.ui.search


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentSearchResultBinding
import com.themovie.di.search.SearchViewModelFactory
import com.themovie.helper.Constant
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Tv
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.TvAdapter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SearchTvFragment : BaseFragment<FragmentSearchResultBinding>(), SwipeRefreshLayout.OnRefreshListener {

    @Inject lateinit var factory: SearchViewModelFactory
    private var query: String? = ""
    private val viewModel by viewModels<SearchTvViewModel> { factory }
    private lateinit var mAdapter: TvAdapter

    override fun getLayout(): Int {
        return R.layout.fragment_search_result
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        (activity as SearchActivity).getComponent()?.inject(this)

        query = getBundle()?.getString("query")
        SearchTvViewModel.query = query.orEmpty()

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
