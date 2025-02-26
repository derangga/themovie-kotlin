package com.themovie.tmdb.ui.main

import android.os.Bundle
import android.view.MotionEvent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.PagerSnapHelper
import com.themovie.core.page.BaseFragment
import com.themovie.core.network.Result
import com.themovie.core.utils.changeActivity
import com.themovie.core.utils.navigateFragment
import com.themovie.datasource.entities.ui.Genre
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.entities.ui.Tv

import com.themovie.tmdb.R
import com.themovie.tmdb.databinding.FragmentHomeBinding
import com.themovie.tmdb.helper.Constant
import com.themovie.tmdb.ui.detail.DetailActivity
import com.themovie.tmdb.ui.main.adapter.GenreAdapter
import com.themovie.tmdb.ui.main.adapter.DiscoverTvAdapter
import com.themovie.tmdb.ui.main.adapter.DiscoverMovieAdapter
import com.themovie.tmdb.ui.main.adapter.UpcomingAdapter
import com.themovie.tmdb.ui.main.adapter.TrendingAdapter

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val trendingAdapter by lazy { TrendingAdapter(::onTrendingMovieClick, ::onTrendingItemTouch) }
    private val upcomingAdapter by lazy { UpcomingAdapter(::onUpcomingMovieItemClick) }
    private val genreAdapter by lazy { GenreAdapter(::onGenreItemClick) }
    private val discoverTvAdapter by lazy { DiscoverTvAdapter(::onDiscoverTvItemClick) }
    private val discoverMovieAdapter by lazy { DiscoverMovieAdapter(::onDiscoverMovieItemClick) }

    private var timer: Timer? = null
    private val viewModel: HomeViewModel by activityViewModels()
    private var isSliding: Boolean = false
    private var isFirstTouch: Boolean = true

    private var currentPosition: Int = 0
    private var sizeOfHeader = 0


    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun onMain(savedInstanceState: Bundle?) {

        binding.header.searchIconVisibility(false)
        recyclerViewSetup()
        onClick()
        viewModel.loading.value?.takeIf { it }?.run {
            subscribeUI()
        }
    }

    override fun onResume() {
        isFirstTouch = true
        startSliding()
        super.onResume()
    }

    override fun onPause() {
        stopSliding()
        super.onPause()
    }

    private fun recyclerViewSetup(){
        binding.apply {
            homePopular.adapter = trendingAdapter
            homeUpcoming.adapter = upcomingAdapter
            homeGenre.adapter = genreAdapter
            homeTv.adapter = discoverTvAdapter
            homeMovies.adapter = discoverMovieAdapter

            val pagerSnapHelper = PagerSnapHelper()
            homePopular.let {
                pagerSnapHelper.attachToRecyclerView(it)
                binding.indicator.attachToRecyclerView(it, pagerSnapHelper)
                trendingAdapter.registerAdapterDataObserver(binding.indicator.adapterDataObserver)
            }
        }

    }

    private fun onClick(){
        binding.apply {
            seeUpcoming.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToUpcomingFragment()
                Navigation.findNavController(it).navigate(action)
            }

            seeGenre.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToGenresFragment()
                Navigation.findNavController(it).navigate(action)
            }

            seeTv.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToTvFragment()
                Navigation.findNavController(it).navigate(action)
            }

            seeMovies.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToMoviesFragment()
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    private fun subscribeUI(){
        lifecycleScope.launch {
            viewModel.trendingMovies.collect { result ->
                when(result) {
                    is Result.Success -> {
                        viewModel.hideLoading()
                        sizeOfHeader = result.data.size
                        trendingAdapter.submitList(result.data)
                    }
                    is Result.Error -> {
                        viewModel.hideLoading()
                        networkErrorDialog.hideRetry()
                        networkErrorDialog.show(childFragmentManager,"")
                    }
                }
            }

            viewModel.upcomingMovies.collect { result ->
                when(result) {
                    is Result.Success -> upcomingAdapter.submitList(result.data)
                    is Result.Error -> Unit
                }
            }

            viewModel.genreMovies.collect { result ->
                when(result) {
                    is Result.Success -> genreAdapter.submitList(result.data)
                    is Result.Error -> Unit
                }
            }

            viewModel.discoverTv.collect { result ->
                when(result) {
                    is Result.Success -> discoverTvAdapter.submitList(result.data)
                    is Result.Error -> Unit
                }
            }

            viewModel.discoverMovies.collect { result ->
                when(result) {
                    is Result.Success -> discoverMovieAdapter.submitList(result.data)
                    is Result.Error -> Unit
                }
            }
        }
    }

    private fun startSliding() {
        if(!isSliding){
            isSliding = true
            timer = Timer().apply {
                scheduleAtFixedRate(SliderTimer(), 0, 5000)
            }
        }
    }

    private fun stopSliding() {
        if(isSliding){
            isSliding = false
            timer?.cancel()
            timer = null
        }
    }

    private fun onTrendingMovieClick(movie: Movie) {
        val bundle = Bundle().apply {
            putInt("filmId", movie.id)
            putString("type", Constant.MOVIE)
        }
        changeActivity<DetailActivity>(bundle)
    }

    private fun onTrendingItemTouch (motionEvent: MotionEvent?) {
        if (motionEvent?.action == MotionEvent.ACTION_DOWN && isFirstTouch) {
            isFirstTouch = false
            stopSliding()
        }
    }

    private fun onUpcomingMovieItemClick(movie: Movie) {
        stopSliding()
        val bundle = Bundle().apply {
            putInt("filmId", movie.id)
            putString("type", Constant.MOVIE)
        }
        changeActivity<DetailActivity>(bundle)
    }

    private fun onGenreItemClick(genre: Genre) {
        val action = HomeFragmentDirections
            .actionHomeFragmentToMovieWithGenreFragment(genre.id, genre.name)
        view.navigateFragment { Navigation.findNavController(it).navigate(action) }
    }

    private fun onDiscoverTvItemClick(tv: Tv) {
        stopSliding()
        val bundle = Bundle().apply {
            putInt("filmId", tv.id)
            putString("type", Constant.TV)
        }
        changeActivity<DetailActivity>(bundle)
    }

    private fun onDiscoverMovieItemClick (movie: Movie) {
        stopSliding()
        val bundle = Bundle().apply {
            putInt("filmId", movie.id)
            putString("type", Constant.MOVIE)
        }
        changeActivity<DetailActivity>(bundle)
    }

    inner class SliderTimer : TimerTask() {
        override fun run() {
            requireActivity().runOnUiThread {
                if(currentPosition < sizeOfHeader - 1){
                    currentPosition++
                    currentPosition.let {
                        binding.homePopular.post {
                            binding.homePopular.smoothScrollToPosition(it)
                        }
                    }

                } else {
                    currentPosition = 0
                    currentPosition.let {
                        binding.homePopular.post {
                            binding.homePopular.smoothScrollToPosition(it)
                        }
                    }
                }
            }
        }
    }
}
