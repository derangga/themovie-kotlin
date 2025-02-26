package com.themovie.tmdb.ui.discover

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.themovie.core.page.BaseFragment
import com.themovie.core.utils.changeActivity
import com.themovie.datasource.entities.ui.Tv

import com.themovie.tmdb.R
import com.themovie.tmdb.databinding.FragmentTvBinding
import com.themovie.tmdb.helper.Constant
import com.themovie.tmdb.ui.detail.DetailActivity
import com.themovie.tmdb.ui.discover.adapter.LoadingStateAdapter
import com.themovie.tmdb.ui.discover.adapter.TvAdapter
import com.themovie.tmdb.ui.search.SuggestActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvFragment : BaseFragment<FragmentTvBinding>() {

    private val mAdapter by lazy { TvAdapter(::onTvShowItemClick) }
    private val viewModel by viewModels<TvViewModel>()

    override fun getLayout(): Int {
        return R.layout.fragment_tv
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this@TvFragment
    }

    override fun onMain(savedInstanceState: Bundle?) {
        setupUIComponent()
        recyclerViewSetup()
        observeDiscoverTv()
    }

    private fun setupUIComponent() {
        binding.header.logoVisibility(false)
            .backButtonVisibility(true)
            .titleText(resources.getString(R.string.home_title_4))
            .backButtonOnClickListener { activity?.onBackPressed() }
            .searchButtonOnClickListener { changeActivity<SuggestActivity>() }
    }

    private fun observeDiscoverTv() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getDiscoverTvPaging().collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    private fun recyclerViewSetup(){
        binding.tvRec.adapter = mAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { mAdapter.retry() },
            footer = LoadingStateAdapter { mAdapter.retry() }
        )

        mAdapter.addLoadStateListener { loadState ->
            binding.tvRec.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            if (loadState.source.refresh is LoadState.Error) {
                networkErrorDialog.show(childFragmentManager, "")
            }
        }
    }

    private fun onTvShowItemClick(tv: Tv) {
        val bundle = Bundle().apply {
            putInt("filmId", tv.id)
            putString("type", Constant.TV)
        }
        changeActivity<DetailActivity>(bundle)
    }

    override fun delegateRetryEventDialog() {
        mAdapter.retry()
    }
}
