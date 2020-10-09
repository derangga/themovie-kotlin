package com.themovie.ui.search


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aldebaran.domain.entities.remote.MovieResponse

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentSearchResultBinding
import com.themovie.helper.Constant
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.changeActivity
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMovieFragment : BaseFragment<FragmentSearchResultBinding>(), SwipeRefreshLayout.OnRefreshListener {

    private var query: String? = ""
    private val viewModel by viewModels<SearchMoviesViewModel>()
    private val mAdapter by lazy { MovieAdapter() }

    override fun getLayout(): Int {
        return R.layout.fragment_search_result
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        query = getBundle()?.getString("query")
        SearchMoviesViewModel.query = query.orEmpty()

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
        mAdapter.apply {
            setOnClickAdapter(object: OnAdapterListener<MovieResponse>{
                override fun onClick(view: View, item: MovieResponse) {
                    val bundle = Bundle().apply {
                        putInt("filmId", item.id ?: 0)
                        putString("type", Constant.MOVIE)
                    }
                    changeActivity<DetailActivity>(bundle)
                }
            })

            setOnErrorClickListener(object: MovieAdapter.OnErrorClickListener{
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
            getSearchMovies().observe(this@SearchMovieFragment, {
                    mAdapter.submitList(it)
                    binding.swipe.isRefreshing = false
                }
            )

            getLoadState().observe(this@SearchMovieFragment, { mAdapter.setLoadState(it) })
        }
    }
}
