package com.themovie.ui.discover


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.themovie.MyApplication

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentMoviesBinding
import com.themovie.helper.Constant
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Movies
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.MovieAdapter
import com.themovie.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.android.synthetic.main.header.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    @Inject lateinit var movieViewFactory: MovieViewModelFactory
    private lateinit var viewModel: MovieViewModel
    private lateinit var mAdapter: MovieAdapter
    private lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        (activity?.application as MyApplication).getAppComponent().inject(this)

        viewModel = ViewModelProvider(this, movieViewFactory).get(MovieViewModel::class.java)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@MoviesFragment
        }
        return binding.root
    }

    override fun onMain(savedInstanceState: Bundle?) {
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
        swipe.setOnRefreshListener(this)
        h_logo.visibility = View.GONE
        h_back.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                val action = MoviesFragmentDirections.actionMoviesFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            }
        }

        h_title.text = resources.getString(R.string.home_title_5)
        h_search.setOnClickListener {
            //val action = MoviesFragmentDirections.actionMoviesFragmentToSearchFragment(Constant.MOVIE)
            //Navigation.findNavController(it).navigate(action)
            changeActivity(SearchActivity::class.java)
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
        mAdapter = MovieAdapter()
        movie_rec.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mAdapter.setOnClickAdapter(object: OnAdapterListener<Movies>{
            override fun onClick(view: View, item: Movies) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id)
                    putString("type", Constant.MOVIE)
                }
                changeActivity(bundle, DetailActivity::class.java)
            }
        })

        mAdapter.setOnErrorClickListener(object: MovieAdapter.OnErrorClickListener{
            override fun onClick(view: View?) {
                viewModel.retry()
            }
        })
    }

    private fun getDiscoverMovies(){
        viewModel.apply {
            getMovieLiveData().observe(this@MoviesFragment,
                Observer<PagedList<Movies>>{
                    mAdapter.submitList(it)
                    swipe.isRefreshing = false
                })

            getLoadState().observe(this@MoviesFragment,
                Observer{
                    mAdapter.setLoadState(it)
                })
        }
    }

}
