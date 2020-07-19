package com.themovie.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.MotionEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentHomeBinding
import com.themovie.di.main.MainViewModelFactory
import com.themovie.helper.*
import com.themovie.model.db.*
import com.themovie.restapi.Result.Status.*
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.main.adapter.*
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject lateinit var factory: MainViewModelFactory
    @Inject lateinit var upcomingAdapter: UpcomingAdapter
    @Inject lateinit var trendingAdapter: TrendingAdapter
    @Inject lateinit var genreAdapter: GenreAdapter
    @Inject lateinit var discoverTvAdapter: DiscoverTvAdapter
    @Inject lateinit var discoverMovieAdapter: DiscoverMovieAdapter

    private var timer: Timer? = null
    private val homeViewModel by viewModels<HomeViewModel> { factory }
    private var isSliding: Boolean = false
    private var isFirstTouch: Boolean = true

    private var currentPosition: Int = 0
    private var sizeOfHeader = 0


    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity as MainActivity).getMainComponent()?.inject(this)
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
        trendingAdapter.setOnClickListener(object: OnAdapterListener<Trending>{
            override fun onClick(view: View, item: Trending) {
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

        upcomingAdapter.setOnClickListener(object: OnAdapterListener<Upcoming>{
            override fun onClick(view: View, item: Upcoming) {
                stopSliding()
                val bundle = Bundle().apply {
                    putInt("filmId", item.id ?: 0)
                    putString("type", Constant.MOVIE)
                }
                changeActivity<DetailActivity>(bundle)
            }
        })

        genreAdapter.setGenreClickListener(object: OnAdapterListener<Genre>{
            override fun onClick(view: View, item: Genre) {
                val action = HomeFragmentDirections
                    .actionHomeFragmentToMovieWithGenreFragment(item.id ?: 0, item.name.orEmpty())
                Navigation.findNavController(view).navigate(action)
            }
        })

        discoverTvAdapter.setOnClickListener(object: OnAdapterListener<Tv>{
            override fun onClick(view: View, item: Tv) {
                stopSliding()
                val bundle = Bundle().apply {
                    putInt("filmId", item.id ?: 0)
                    putString("type", Constant.TV)
                }
                changeActivity<DetailActivity>(bundle)
            }
        })

        discoverMovieAdapter.setOnClickListener(object: OnAdapterListener<Movies>{
            override fun onClick(view: View, item: Movies) {
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
            trendingMovies.observe(viewLifecycleOwner, Observer { res ->
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

            upcomingMovies.observe(viewLifecycleOwner, Observer { res ->
                 when(res.status){
                     SUCCESS -> {
                         upcomingAdapter.submitList(res.data)
                     }
                     else -> {}
                 }
            })

            genreMovies.observe( viewLifecycleOwner, Observer { res ->
                 when(res.status){
                     SUCCESS -> {
                         genreAdapter.submitList(res.data)
                     }
                     else -> {}
                 }
            })

            discoverTv.observe(viewLifecycleOwner, Observer { res ->
                when(res.status){
                    SUCCESS -> {
                        discoverTvAdapter.submitList(res.data)
                    }
                    else -> {}
                }
            })

            discoverMovies.observe(viewLifecycleOwner, Observer { res ->
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
