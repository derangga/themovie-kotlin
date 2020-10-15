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
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.utils.changeActivity
import com.aldebaran.utils.initLinearRecycler

import com.themovie.R
import com.themovie.databinding.FragmentMoviesBinding
import com.themovie.helper.Constant
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.LoadingStateAdapter
import com.themovie.ui.discover.adapter.MovieAdapter
import com.themovie.ui.search.SuggestActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {

    private val viewModel by viewModels<MovieViewModel>()
    private val mAdapter by lazy { MovieAdapter(::onMovieItemClick) }

    override fun getLayout(): Int {
        return R.layout.fragment_movies
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this@MoviesFragment
    }

    override fun onMain(savedInstanceState: Bundle?) {
        setupUIComponent()
        recyclerViewSetup()
        observeDiscoverMovie()
    }

    private fun setupUIComponent(){
        binding.header.apply {
            setLogoVisibility(View.GONE)
            setBackButtonVisibility(View.VISIBLE)
            setTitleText(resources.getString(R.string.home_title_5))
            setBackButtonOnClickListener {
                val action = MoviesFragmentDirections.actionMoviesFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            }

            setSearchButtonOnClickListener { changeActivity<SuggestActivity>() }
        }


        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val action = MoviesFragmentDirections.actionMoviesFragmentToHomeFragment()
                Navigation.findNavController(view!!).navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun observeDiscoverMovie() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getDiscoverMoviePaging("").collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    private fun recyclerViewSetup(){
        binding.movieRec.initLinearRecycler(requireContext())
        binding.movieRec.adapter = mAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { mAdapter.retry() },
            footer = LoadingStateAdapter { mAdapter.retry() }
        )

        mAdapter.addLoadStateListener { loadState ->
            binding.movieRec.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            if (loadState.source.refresh is LoadState.Error) {
                networkErrorDialog.show(childFragmentManager, "")
            }
        }
    }

    private fun onMovieItemClick(movie: MovieResponse) {
        val bundle = Bundle().apply {
            putInt("filmId", movie.id ?: 0)
            putString("type", Constant.MOVIE)
        }
        changeActivity<DetailActivity>(bundle)
    }

    override fun delegateRetryEventDialog() {
        mAdapter.retry()
    }
}
