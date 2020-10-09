package com.themovie.ui.main


import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentHomeBinding
import com.themovie.helper.*
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.main.adapter.*
import com.aldebaran.domain.Result.Status.*
import com.aldebaran.domain.entities.Genre
import com.aldebaran.domain.entities.Movie
import com.aldebaran.domain.entities.Tv
import com.aldebaran.domain.entities.local.*

import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val upcomingAdapter by lazy { UpcomingAdapter() }
    private val trendingAdapter by lazy { TrendingAdapter() }
    private val genreAdapter by lazy { GenreAdapter() }
    private val discoverTvAdapter by lazy { DiscoverTvAdapter() }
    private val discoverMovieAdapter by lazy { DiscoverMovieAdapter() }

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
                activity?.finishAffinity()
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
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = trendingAdapter
            }

            homeUpcoming.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = upcomingAdapter
            }

            homeGenre.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = genreAdapter
            }

            homeTv.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = discoverTvAdapter
            }

            homeMovies.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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
        trendingAdapter.setOnClickListener(object: OnAdapterListener<TrendingEntity>{
            override fun onClick(view: View, item: TrendingEntity) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id ?: 0)
                    putString("type", Constant.MOVIE)
                }
                changeActivity<DetailActivity>(bundle)
            }
        })

        trendingAdapter.setOnTouchListener(object: TrendingAdapter.OnTouchAdapterListener{
            override fun onTouchItem(view: View?, motionEvent: MotionEvent?) {
                if(motionEvent?.action == MotionEvent.ACTION_DOWN && isFirstTouch){
                    isFirstTouch = false
                    stopSliding()
                }
            }
        })

        upcomingAdapter.setOnClickListener(object: OnAdapterListener<UpcomingEntity>{
            override fun onClick(view: View, item: UpcomingEntity) {
                stopSliding()
                val bundle = Bundle().apply {
                    putInt("filmId", item.id ?: 0)
                    putString("type", Constant.MOVIE)
                }
                changeActivity<DetailActivity>(bundle)
            }
        })

        genreAdapter.setGenreClickListener(object: OnAdapterListener<GenreEntity>{
            override fun onClick(view: View, item: GenreEntity) {
                val action = HomeFragmentDirections
                    .actionHomeFragmentToMovieWithGenreFragment(item.id ?: 0, item.name.orEmpty())
                Navigation.findNavController(view).navigate(action)
            }
        })

        discoverTvAdapter.setOnClickListener(object: OnAdapterListener<TvEntity>{
            override fun onClick(view: View, item: TvEntity) {
                stopSliding()
                val bundle = Bundle().apply {
                    putInt("filmId", item.id ?: 0)
                    putString("type", Constant.TV)
                }
                changeActivity<DetailActivity>(bundle)
            }
        })

        discoverMovieAdapter.setOnClickListener(object: OnAdapterListener<MovieEntity>{
            override fun onClick(view: View, item: MovieEntity) {
                stopSliding()
                val bundle = Bundle().apply {
                    putInt("filmId", item.id ?: 0)
                    putString("type", Constant.MOVIE)
                }
                changeActivity<DetailActivity>(bundle)
            }
        })

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
                        showNetworkError(true){}
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
                scheduleAtFixedRate(SliderTimer(), 5000, 5000)
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

    inner class SliderTimer : TimerTask() {
        override fun run() {
            activity?.runOnUiThread {
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
