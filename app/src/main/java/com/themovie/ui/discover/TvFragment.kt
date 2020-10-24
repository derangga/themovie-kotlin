package com.themovie.ui.discover

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import com.aldebaran.core.BaseFragment
import com.aldebaran.domain.entities.remote.TvResponse
import com.aldebaran.utils.changeActivity
import com.aldebaran.utils.initLinearRecycler

import com.themovie.R
import com.themovie.databinding.FragmentTvBinding
import com.themovie.helper.Constant
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.LoadingStateAdapter
import com.themovie.ui.discover.adapter.TvAdapter
import com.themovie.ui.search.SuggestActivity
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
        binding.header.apply {
            setLogoVisibility(View.GONE)
            setTitleText(resources.getString(R.string.home_title_4))
            setBackButtonVisibility(View.VISIBLE)
            setBackButtonOnClickListener {
                val action = TvFragmentDirections.actionTvFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            }
            setSearchButtonOnClickListener { changeActivity<SuggestActivity>() }
        }


        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val action = TvFragmentDirections.actionTvFragmentToHomeFragment()
                Navigation.findNavController(view!!).navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun observeDiscoverTv() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getDiscoverTvPaging().collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    private fun recyclerViewSetup(){
        binding.tvRec.initLinearRecycler(requireContext())
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

    private fun onTvShowItemClick(tv: TvResponse) {
        val bundle = Bundle().apply {
            putInt("filmId", tv.id ?: 0)
            putString("type", Constant.TV)
        }
        changeActivity<DetailActivity>(bundle)
    }

    override fun delegateRetryEventDialog() {
        mAdapter.retry()
    }
}
