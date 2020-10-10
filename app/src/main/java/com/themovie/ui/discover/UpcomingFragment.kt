package com.themovie.ui.discover


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aldebaran.base.BaseFragment
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.utils.changeActivity
import com.aldebaran.utils.initLinearRecycler

import com.themovie.R
import com.themovie.databinding.FragmentUpcomingBinding
import com.themovie.helper.Constant
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.UpcomingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : BaseFragment<FragmentUpcomingBinding>(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by viewModels<UpComingViewModel>()
    private val mAdapter by lazy { UpcomingAdapter(::onMovieItemClick, ::onLoadMoreRetry) }

    override fun getLayout(): Int {
        return R.layout.fragment_upcoming
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@UpcomingFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        binding.swipe.setOnRefreshListener(this)
        binding.header.apply {
            setLogoVisibility(View.GONE)
            setSearchVisibility(View.GONE)
            setBackButtonVisibility(View.VISIBLE)
            setTitleText(resources.getString(R.string.home_title_2))
            setBackButtonOnClickListener {
                val action = UpcomingFragmentDirections.actionUpcomingFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            }
        }

        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val action = UpcomingFragmentDirections.actionUpcomingFragmentToHomeFragment()
                Navigation.findNavController(view!!).navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        recyclerViewSetup()
    }

    override fun onStart() {
        super.onStart()
        getUpcomingMovie()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSubscribing()
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    private fun recyclerViewSetup(){
        binding.upcomingRec.apply {
            initLinearRecycler(requireContext())
            adapter = mAdapter
        }
    }

    private fun getUpcomingMovie(){
        viewModel.apply {
            getUpcomingData().observe(this@UpcomingFragment, {
                    mAdapter.submitList(it)
                    binding.swipe.isRefreshing = false
                })

            getLoadState().observe(this@UpcomingFragment, { mAdapter.setLoadState(it) })
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
