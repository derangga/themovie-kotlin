package com.themovie.tmdb.ui.search


import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.themovie.core.page.BaseFragment
import com.themovie.core.utils.changeActivity
import com.themovie.datasource.entities.ui.Movie

import com.themovie.tmdb.R
import com.themovie.tmdb.databinding.FragmentSuggestBinding
import com.themovie.tmdb.helper.Constant
import com.themovie.tmdb.ui.detail.DetailActivity
import com.themovie.tmdb.ui.search.adapter.SuggestMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuggestMovieFragment : BaseFragment<FragmentSuggestBinding>() {

    private val viewModel by activityViewModels<SuggestViewModel>()
    private val mAdapter by lazy { SuggestMoviesAdapter(::onMovieItemClick) }

    override fun getLayout(): Int {
        return R.layout.fragment_suggest
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
    }

    override fun onMain(savedInstanceState: Bundle?) {
        setupRecyclerView()
        observeSuggestData()
    }

    private fun setupRecyclerView(){
        binding.recyclerView.adapter = mAdapter

    }

    private fun observeSuggestData(){
        viewModel.movieSearch.observe(viewLifecycleOwner, {
            mAdapter.submitList(it)
        })

        viewModel.searchText.observe(viewLifecycleOwner, {
            viewModel.fetchSuggestMovie(it)
        })
    }

    private fun onMovieItemClick(movie: Movie) {
        val bundle = Bundle().apply {
            putInt("filmId", movie.id)
            putString("type", Constant.MOVIE)
        }
        changeActivity<DetailActivity>(bundle)
        activity?.finish()
    }
}
