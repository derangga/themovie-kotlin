package com.themovie.ui.discover

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.aldebaran.core.BaseFragment
import com.aldebaran.domain.entities.ui.Movie
import com.aldebaran.utils.changeActivity

import com.themovie.R
import com.themovie.databinding.FragmentUpcomingBinding
import com.themovie.helper.Constant
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.LoadingStateAdapter
import com.themovie.ui.discover.adapter.UpcomingAdapter
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
        binding.header.apply {
            setLogoVisibility(View.GONE)
            setSearchVisibility(View.GONE)
            setBackButtonVisibility(View.VISIBLE)
            setTitleText(resources.getString(R.string.home_title_2))
            setBackButtonOnClickListener {
                activity?.onBackPressed()
            }
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
