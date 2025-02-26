package com.themovie.tmdb.ui.discover

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.themovie.core.page.BaseFragment
import com.themovie.core.utils.changeActivity
import com.themovie.datasource.entities.ui.Movie

import com.themovie.tmdb.R
import com.themovie.tmdb.databinding.FragmentUpcomingBinding
import com.themovie.tmdb.helper.Constant
import com.themovie.tmdb.ui.detail.DetailActivity
import com.themovie.tmdb.ui.discover.adapter.LoadingStateAdapter
import com.themovie.tmdb.ui.discover.adapter.UpcomingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpcomingFragment : BaseFragment<FragmentUpcomingBinding>() {

    private val viewModel by viewModels<UpComingViewModel>()
    private val mAdapter by lazy { UpcomingAdapter(::onMovieItemClick) }

    override fun getLayout(): Int {
        return R.layout.fragment_upcoming
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this@UpcomingFragment

    }

    override fun onMain(savedInstanceState: Bundle?) {
        setupUIComponent()
        recyclerViewSetup()
        observeDiscoverTv()
    }

    private fun observeDiscoverTv() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getUpcomingMoviePaging().collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    private fun setupUIComponent() {
        binding.header.logoVisibility(false)
            .searchIconVisibility(false)
            .backButtonVisibility(true)
            .titleText(resources.getString(R.string.home_title_2))
            .backButtonOnClickListener {
                activity?.onBackPressed()
            }
    }

    private fun recyclerViewSetup(){
        binding.upcomingRec.adapter = mAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { mAdapter.retry() },
            footer = LoadingStateAdapter { mAdapter.retry() }
        )

        mAdapter.addLoadStateListener { loadState ->
            binding.upcomingRec.isVisible = loadState.source.refresh is LoadState.NotLoading
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
