package com.themovie.ui.genres

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
import com.themovie.R
import com.themovie.databinding.FragmentMoviesBinding
import com.themovie.helper.Constant
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.MovieViewModel
import com.themovie.ui.discover.adapter.LoadingStateAdapter
import com.themovie.ui.discover.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieWithGenreFragment : BaseFragment<FragmentMoviesBinding>() {

    private val viewModel by viewModels<MovieViewModel>()
    private val mAdapter by lazy { MovieAdapter(::onMovieItemClick) }

    private var destinationBackPress = ""
    private var title = ""
    private var genreId = ""

    override fun getLayout(): Int {
        return R.layout.fragment_movies
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        arguments?.let {
            genreId = MovieWithGenreFragmentArgs.fromBundle(it).genreId.toString()
            destinationBackPress = MovieWithGenreFragmentArgs.fromBundle(it).from
            title = MovieWithGenreFragmentArgs.fromBundle(it).genreName
        }

        binding.lifecycleOwner = this@MovieWithGenreFragment

    }

    override fun onMain(savedInstanceState: Bundle?) {
        setupUIComponent()
        recyclerViewSetup()
        observeDiscoverMovie()
    }

    private fun setupUIComponent() {
        val action = if(destinationBackPress == "home"){
            MovieWithGenreFragmentDirections.actionMovieWithGenreFragmentToHomeFragment()
        } else MovieWithGenreFragmentDirections.actionMovieWithGenreFragmentToGenresFragment()

        binding.header.apply {
            setLogoVisibility(View.GONE)
            setBackButtonVisibility(View.VISIBLE)
            setSearchVisibility(View.GONE)
            setTitleText("Genres: $title")
            setBackButtonOnClickListener {
                Navigation.findNavController(it).navigate(action)
            }
        }

        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() { Navigation.findNavController(view!!).navigate(action) }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun observeDiscoverMovie() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getDiscoverMoviePaging(genreId).collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    private fun recyclerViewSetup(){
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
