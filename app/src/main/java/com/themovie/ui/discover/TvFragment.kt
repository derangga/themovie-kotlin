package com.themovie.ui.discover


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aldebaran.base.BaseFragment
import com.aldebaran.domain.entities.remote.TvResponse
import com.aldebaran.utils.changeActivity
import com.aldebaran.utils.initLinearRecycler

import com.themovie.R
import com.themovie.databinding.FragmentTvBinding
import com.themovie.helper.Constant
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.TvAdapter
import com.themovie.ui.search.SuggestActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvFragment : BaseFragment<FragmentTvBinding>(), SwipeRefreshLayout.OnRefreshListener {

    private val tvAdapter by lazy { TvAdapter(::onTvShowItemClick, ::onLoadMoreRetry) }
    private val viewModel by viewModels<TvViewModel>()

    override fun getLayout(): Int {
        return R.layout.fragment_tv
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@TvFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        binding.swipe.setOnRefreshListener(this)
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
        recyclerViewSetup()
    }

    override fun onStart() {
        super.onStart()
        getDiscoverTv()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSubscribing()
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    private fun recyclerViewSetup(){
        binding.tvRec.apply {
            initLinearRecycler(requireContext())
            adapter = tvAdapter
        }
    }

    private fun getDiscoverTv(){
        viewModel.apply {
            getTvLiveData().observe(this@TvFragment, {
                    tvAdapter.submitList(it)
                    binding.swipe.isRefreshing = false
                })

            getLoadState().observe(this@TvFragment, {
                    tvAdapter.setLoadState(it)
                })
        }
    }

    private fun onTvShowItemClick(tv: TvResponse) {
        val bundle = Bundle().apply {
            putInt("filmId", tv.id ?: 0)
            putString("type", Constant.TV)
        }
        changeActivity<DetailActivity>(bundle)
    }

    private fun onLoadMoreRetry() {
        viewModel.retry()
    }
}
