package com.themovie.tmdb.ui.genres

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.themovie.core.page.BaseFragment
import com.themovie.core.utils.changeActivity
import com.themovie.datasource.entities.ui.Movie
import com.themovie.tmdb.R
import com.themovie.tmdb.databinding.FragmentMoviesBinding
import com.themovie.tmdb.helper.Constant
import com.themovie.tmdb.ui.detail.DetailActivity
import com.themovie.tmdb.ui.discover.MovieViewModel
import com.themovie.tmdb.ui.discover.adapter.LoadingStateAdapter
import com.themovie.tmdb.ui.discover.adapter.MovieAdapter
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
        binding.header.logoVisibility(false)
            .backButtonVisibility(true)
            .searchIconVisibility(false)
            .titleText("Genres: $title")
            .backButtonOnClickListener {
                activity?.onBackPressed()
            }
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
