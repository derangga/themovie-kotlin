package com.themovie.ui.search


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovie.MyApplication

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentSuggestBinding
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Movies
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.search.adapter.SuggestMoviesAdapter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SuggestMovieFragment : BaseFragment<FragmentSuggestBinding>(), SuggestActivity.MoviesSearchFragmentListener {

    @Inject lateinit var viewModelFactory: SuggestMoviesFactory
    private lateinit var viewModel: SuggestMoviesViewModel
    private lateinit var mAdapter: SuggestMoviesAdapter

    override fun getLayout(): Int {
        return R.layout.fragment_suggest
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity?.application as MyApplication).getAppComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SuggestMoviesViewModel::class.java)
        binding.lifecycleOwner = this
    }

    override fun onMain(savedInstanceState: Bundle?) {
        SuggestActivity.setTextListener(this)
        setupRecyclerView()
        observeSuggestData()
        getLoadStatus()
    }

    private fun setupRecyclerView(){
        mAdapter = SuggestMoviesAdapter()
        mAdapter.setAdapterListener(object: OnAdapterListener<Movies>{
            override fun onClick(view: View, item: Movies) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id)
                    putString("type", Constant.MOVIE)
                }
                changeActivity(bundle, DetailActivity::class.java)
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
            Observer {
                mAdapter.submitList(it)
            })
    }

    private fun getLoadStatus(){
        viewModel.getLoadStatus().observe(this, Observer {
            if(it == LoadDataState.LOADED) binding.recyclerView.visibility = View.VISIBLE
            else binding.recyclerView.visibility = View.GONE
        })
    }

    override fun textChange(text: String) {
        viewModel.fetchSuggestMovie(text)
    }
}
