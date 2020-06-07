package com.themovie.ui.discover

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentMoviesBinding
import com.themovie.di.main.MainViewModelFactory
import com.themovie.helper.Constant
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Movies
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.MovieAdapter
import com.themovie.ui.main.MainActivity
import com.themovie.ui.search.SuggestActivity
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(), SwipeRefreshLayout.OnRefreshListener {

    @Inject lateinit var factory: MainViewModelFactory
    private val viewModel by viewModels<MovieViewModel>{ factory }
    private lateinit var mAdapter: MovieAdapter

    override fun getLayout(): Int {
        return R.layout.fragment_movies
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity as MainActivity).getMainComponent()?.inject(this)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@MoviesFragment
        }
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
        binding.header.apply {
            setLogoVisibility(View.GONE)
            setBackButtonVisibility(View.VISIBLE)
            setTitleText(resources.getString(R.string.home_title_5))
            setBackButtonOnClickListener(View.OnClickListener {
                val action = MoviesFragmentDirections.actionMoviesFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            })

            setSearchButtonOnClickListener(View.OnClickListener { changeActivity(SuggestActivity::class.java) })
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
                Observer{ mAdapter.setLoadState(it) })
        }
    }

}
