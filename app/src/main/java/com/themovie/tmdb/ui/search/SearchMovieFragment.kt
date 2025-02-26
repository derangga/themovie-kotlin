package com.themovie.tmdb.ui.search

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.themovie.core.page.BaseFragment
import com.themovie.core.utils.changeActivity
import com.themovie.datasource.entities.ui.Movie

import com.themovie.tmdb.R
import com.themovie.tmdb.databinding.FragmentSearchResultBinding
import com.themovie.tmdb.helper.Constant
import com.themovie.tmdb.ui.detail.DetailActivity
import com.themovie.tmdb.ui.discover.adapter.LoadingStateAdapter
import com.themovie.tmdb.ui.discover.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMovieFragment : BaseFragment<FragmentSearchResultBinding>() {

    private val viewModel by activityViewModels<SearchViewModel>()
    private val mAdapter by lazy { MovieAdapter(::onMovieItemClick) }

    override fun getLayout(): Int {
        return R.layout.fragment_search_result
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this

    }

    override fun onMain(savedInstanceState: Bundle?) {
        setupRecyclerView()
        observePaging()
    }

    private fun observePaging() {
        val query = getBundle()?.getString("query").orEmpty()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getDiscoverMoviePaging(query).collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    private fun setupRecyclerView(){
        binding.recyclerView.adapter = mAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { mAdapter.retry() },
            footer = LoadingStateAdapter { mAdapter.retry() }
        )

        mAdapter.addLoadStateListener { loadState ->
            binding.recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            if (loadState.source.refresh is LoadState.Error) {
                networkErrorDialog.show(childFragmentManager, "")
            }
        }
    }

    private fun onMovieItemClick(movie: Movie) {
        val bundle = Bundle().apply {
            putInt("filmId", movie.id)
            putString("type", Constant.MOVIE)
        }
        changeActivity<DetailActivity>(bundle)
    }

    override fun delegateRetryEventDialog() {
        mAdapter.retry()
    }
}
