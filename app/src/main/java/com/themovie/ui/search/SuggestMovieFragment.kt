package com.themovie.ui.search


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentSuggestBinding
import com.themovie.di.suggest.SuggestViewModelFactory
import com.themovie.helper.*
import com.themovie.model.db.Movies
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.restapi.Result
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.search.adapter.SuggestMoviesAdapter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SuggestMovieFragment : BaseFragment<FragmentSuggestBinding>(), SuggestActivity.MoviesSearchFragmentListener {

    @Inject lateinit var factory: SuggestViewModelFactory
    private val viewModel by viewModels<SuggestMoviesViewModel> { factory }
    private lateinit var mAdapter: SuggestMoviesAdapter

    override fun getLayout(): Int {
        return R.layout.fragment_suggest
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity as SuggestActivity).getComponent()?.inject(this)
        binding.lifecycleOwner = this
    }

    override fun onMain(savedInstanceState: Bundle?) {
        SuggestActivity.setTextListener(this)
        setupRecyclerView()
        observeSuggestData()
    }

    private fun setupRecyclerView(){
        mAdapter = SuggestMoviesAdapter()
        mAdapter.setAdapterListener(object: OnAdapterListener<Movies>{
            override fun onClick(view: View, item: Movies) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id)
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
        viewModel.getSuggestMovies().observe(this,
            Observer<Result<MoviesResponse>>{
                when(it.status){
                    Result.Status.LOADING -> {}
                    Result.Status.SUCCESS -> {
                        if(binding.recyclerView.visibility == View.GONE)
                            binding.recyclerView.visible()
                        mAdapter.submitList(it.data?.movies)
                    }
                    Result.Status.ERROR -> { binding.recyclerView.gone()}
                }
            })
    }

    override fun textChange(text: String) {
        viewModel.fetchSuggestMovie(text)
    }
}
