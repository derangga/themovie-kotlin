package com.themovie.ui.search


import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aldebaran.base.BaseFragment
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.utils.changeActivity
import com.aldebaran.utils.initLinearRecycler

import com.themovie.R
import com.themovie.databinding.FragmentSearchResultBinding
import com.themovie.helper.Constant
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMovieFragment : BaseFragment<FragmentSearchResultBinding>(), SwipeRefreshLayout.OnRefreshListener {

    private var query: String? = ""
    private val viewModel by viewModels<SearchMoviesViewModel>()
    private val mAdapter by lazy { MovieAdapter(::onMovieItemClick, ::onLoadMoreRetry) }

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
        binding.recyclerView.apply {
            initLinearRecycler(requireContext())
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

    private fun onMovieItemClick(movie: MovieResponse) {
        val bundle = Bundle().apply {
            putInt("filmId", movie.id ?: 0)
            putString("type", Constant.MOVIE)
        }
        changeActivity<DetailActivity>(bundle)
    }

    private fun onLoadMoreRetry() {
        viewModel.retry()
    }
}
