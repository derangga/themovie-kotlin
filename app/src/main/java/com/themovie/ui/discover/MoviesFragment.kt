package com.themovie.ui.discover

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aldebaran.domain.entities.remote.MovieResponse

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentMoviesBinding
import com.themovie.helper.Constant
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.changeActivity
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.MovieAdapter
import com.themovie.ui.search.SuggestActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by viewModels<MovieViewModel>()
    private val mAdapter by lazy { MovieAdapter() }

    override fun getLayout(): Int {
        return R.layout.fragment_movies
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@MoviesFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        viewModel.resetMovieWithGenre("")
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
        binding.swipe.setOnRefreshListener(this)
        binding.header.apply {
            setLogoVisibility(View.GONE)
            setBackButtonVisibility(View.VISIBLE)
            setTitleText(resources.getString(R.string.home_title_5))
            setBackButtonOnClickListener {
                val action = MoviesFragmentDirections.actionMoviesFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            }

            setSearchButtonOnClickListener({ changeActivity<SuggestActivity>() })
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
        binding.movieRec.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mAdapter.setOnClickAdapter(object: OnAdapterListener<MovieResponse>{
            override fun onClick(view: View, item: MovieResponse) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id ?: 0)
                    putString("type", Constant.MOVIE)
                }
                changeActivity<DetailActivity>(bundle)
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
            getMovieLiveData().observe(this@MoviesFragment, {
                    mAdapter.submitList(it)
                    binding.swipe.isRefreshing = false
                })

            getLoadState().observe(this@MoviesFragment,
                { mAdapter.setLoadState(it) })
        }
    }

}
