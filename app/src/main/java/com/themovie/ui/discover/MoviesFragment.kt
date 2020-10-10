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
import com.themovie.databinding.FragmentMoviesBinding
import com.themovie.helper.Constant
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.MovieAdapter
import com.themovie.ui.search.SuggestActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by viewModels<MovieViewModel>()
    private val mAdapter by lazy { MovieAdapter(::onMovieItemClick, ::onLoadMoreRetry) }

    override fun getLayout(): Int {
        return R.layout.fragment_movies
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@MoviesFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        viewModel.resetMovieWithGenre("")
        setupUIComponent()
        recyclerViewSetup()
    }

    override fun onStart() {
        super.onStart()
        getDiscoverMovies()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSubscribing()
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    private fun setupUIComponent(){
        binding.swipe.setOnRefreshListener(this)
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

    private fun recyclerViewSetup(){
        binding.movieRec.initLinearRecycler(requireContext())
        binding.movieRec.adapter = mAdapter
    }

    private fun getDiscoverMovies(){
        viewModel.apply {
            getMovieLiveData().observe(this@MoviesFragment, {
                    mAdapter.submitList(it)
                    binding.swipe.isRefreshing = false
            })

            getLoadState().observe(this@MoviesFragment, { mAdapter.setLoadState(it) })
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
