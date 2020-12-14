package com.themovie.ui.search


import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.aldebaran.core.BaseFragment
import com.aldebaran.domain.entities.ui.Movie
import com.aldebaran.utils.changeActivity

import com.themovie.R
import com.themovie.databinding.FragmentSuggestBinding
import com.themovie.helper.*
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.search.adapter.SuggestMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuggestMovieFragment : BaseFragment<FragmentSuggestBinding>(), SuggestActivity.MoviesSearchFragmentListener {

    private val viewModel by activityViewModels<SuggestViewModel>()
    private val mAdapter by lazy { SuggestMoviesAdapter(::onMovieItemClick) }

    override fun getLayout(): Int {
        return R.layout.fragment_suggest
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
    }

    override fun onMain(savedInstanceState: Bundle?) {
        SuggestActivity.setTextListener(this)
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
    }

    override fun textChange(text: String) {
        viewModel.fetchSuggestMovie(text)
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
