package com.themovie.ui.search


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.Result.Status.*

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentSuggestBinding
import com.themovie.helper.*
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.search.adapter.SuggestMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuggestMovieFragment : BaseFragment<FragmentSuggestBinding>(), SuggestActivity.MoviesSearchFragmentListener {

    private val viewModel by viewModels<SuggestMoviesViewModel>()
    private val mAdapter by lazy { SuggestMoviesAdapter() }

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
        mAdapter.setAdapterListener(object: OnAdapterListener<MovieResponse>{
            override fun onClick(view: View, item: MovieResponse) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id ?: 0)
                    putString("type", Constant.MOVIE)
                }
                changeActivity<DetailActivity>(bundle)
                activity?.finish()
            }
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun observeSuggestData(){
        viewModel.getSuggestMovies().observe(this, { res ->
                when(res.status){
                    LOADING -> {}
                    SUCCESS -> {
                        if(binding.recyclerView.visibility == View.GONE)
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
}
