package com.themovie.ui.search


import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.aldebaran.core.BaseFragment
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.Result.Status.*
import com.aldebaran.utils.changeActivity
import com.aldebaran.utils.gone
import com.aldebaran.utils.initLinearRecycler
import com.aldebaran.utils.visible

import com.themovie.R
import com.themovie.databinding.FragmentSuggestBinding
import com.themovie.helper.*
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.search.adapter.SuggestMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuggestMovieFragment : BaseFragment<FragmentSuggestBinding>(), SuggestActivity.MoviesSearchFragmentListener {

    private val viewModel by viewModels<SuggestMoviesViewModel>()
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
        binding.recyclerView.apply {
            initLinearRecycler(requireContext())
            adapter = mAdapter
        }
    }

    private fun observeSuggestData(){
        viewModel.getSuggestMovies().observe(this, { res ->
                when(res.status){
                    LOADING -> {}
                    SUCCESS -> {
                        if(!binding.recyclerView.isVisible)
                            binding.recyclerView.visible()
                        mAdapter.submitList(res.data?.results)
                    }
                    ERROR -> { binding.recyclerView.gone()}
                }
            })
    }

    override fun textChange(text: String) {
        viewModel.fetchSuggestMovie(text)
    }

    private fun onMovieItemClick(movie: MovieResponse) {
        val bundle = Bundle().apply {
            putInt("filmId", movie.id ?: 0)
            putString("type", Constant.MOVIE)
        }
        changeActivity<DetailActivity>(bundle)
        activity?.finish()
    }
}
