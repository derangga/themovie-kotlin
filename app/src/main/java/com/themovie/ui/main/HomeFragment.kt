package com.themovie.ui.main


import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.core.BaseFragment

import com.themovie.R
import com.themovie.databinding.FragmentHomeBinding
import com.themovie.helper.*
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.main.adapter.*
import com.aldebaran.domain.Result.Status.*
import com.aldebaran.domain.entities.local.*
import com.aldebaran.utils.*

import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val trendingAdapter by lazy { TrendingAdapter(::onTrendingMovieClick, ::onTrendingItemTouch) }
    private val upcomingAdapter by lazy { UpcomingAdapter(::onUpcomingMovieItemClick) }
    private val genreAdapter by lazy { GenreAdapter(::onGenreItemClick) }
    private val discoverTvAdapter by lazy { DiscoverTvAdapter(::onDiscoverTvItemClick) }
    private val discoverMovieAdapter by lazy { DiscoverMovieAdapter(::onDiscoverMovieItemClick) }

    private var timer: Timer? = null
    private val homeViewModel by viewModels<HomeViewModel>()
    private var isSliding: Boolean = false
    private var isFirstTouch: Boolean = true

    private var currentPosition: Int = 0
    private var sizeOfHeader = 0


    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.apply {
            vm = homeViewModel
            lifecycleOwner = this@HomeFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {

        binding.header.setSearchVisibility(View.GONE)
        recyclerViewSetup()
        onClick()
        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finishAffinity()
            }}
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        subscribeUI()
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
            homePopular.apply {
                initLinearRecycler(requireContext(), RecyclerView.HORIZONTAL)
                adapter = trendingAdapter
            }

            homeUpcoming.apply {
                initLinearRecycler(requireContext(), RecyclerView.HORIZONTAL)
                adapter = upcomingAdapter
            }

            homeGenre.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = genreAdapter
            }

            homeTv.apply {
                initLinearRecycler(requireContext(), RecyclerView.HORIZONTAL)
                adapter = discoverTvAdapter
            }

            homeMovies.apply {
                initLinearRecycler(requireContext(), RecyclerView.HORIZONTAL)
                adapter = discoverMovieAdapter
            }

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
            seeUpco.setOnClickListener {
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
        homeViewModel.apply {
            trendingMovies.observe(viewLifecycleOwner, { res ->
                when(res.status){
                    SUCCESS -> {
                        hideLoading()
                        sizeOfHeader = res.data?.size ?: 0
                        trendingAdapter.submitList(res.data)
                    }
                    ERROR -> {
                        hideLoading()
                        networkErrorDialog.hideRetry()
                        networkErrorDialog.show(childFragmentManager, "")
                    }
                    LOADING -> {
                        showLoading()
                    }
                }
            })

            upcomingMovies.observe(viewLifecycleOwner, { res ->
                 when(res.status){
                     SUCCESS -> {
                         upcomingAdapter.submitList(res.data)
                     }
                     else -> {}
                 }
            })

            genreMovies.observe( viewLifecycleOwner, { res ->
                 when(res.status){
                     SUCCESS -> {
                         genreAdapter.submitList(res.data)
                     }
                     else -> {}
                 }
            })

            discoverTv.observe(viewLifecycleOwner, { res ->
                when(res.status){
                    SUCCESS -> {
                        discoverTvAdapter.submitList(res.data)
                    }
                    else -> {}
                }
            })

            discoverMovies.observe(viewLifecycleOwner, { res ->
                when(res.status){
                    SUCCESS -> {
                        discoverMovieAdapter.submitList(res.data)
                    }
                    else -> {}
                }
            })
        }
    }

    private fun hideLoading(){
        binding.apply {
            shimmerHome.gone()
            homeLayout.visible()
        }
    }

    private fun showLoading(){
        binding.apply {
            shimmerHome.visible()
            homeLayout.gone()
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

    private fun onTrendingMovieClick(movie: TrendingEntity) {
        val bundle = Bundle().apply {
            putInt("filmId", movie.id ?: 0)
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

    private fun onUpcomingMovieItemClick(movie: UpcomingEntity) {
        stopSliding()
        val bundle = Bundle().apply {
            putInt("filmId", movie.id ?: 0)
            putString("type", Constant.MOVIE)
        }
        changeActivity<DetailActivity>(bundle)
    }

    private fun onGenreItemClick(genre: GenreEntity) {
        val action = HomeFragmentDirections
            .actionHomeFragmentToMovieWithGenreFragment(genre.id ?: 0, genre.name.orEmpty())
        view?.navigateFragment { Navigation.findNavController(it).navigate(action) }
    }

    private fun onDiscoverTvItemClick(tv: TvEntity) {
        stopSliding()
        val bundle = Bundle().apply {
            putInt("filmId", tv.id ?: 0)
            putString("type", Constant.TV)
        }
        changeActivity<DetailActivity>(bundle)
    }

    private fun onDiscoverMovieItemClick (movie: MovieEntity) {
        stopSliding()
        val bundle = Bundle().apply {
            putInt("filmId", movie.id ?: 0)
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
