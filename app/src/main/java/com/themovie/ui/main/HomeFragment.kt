package com.themovie.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.MotionEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.themovie.MyApplication

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentHomeBinding
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.*
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.main.adapter.*
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>(), SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var homeViewFactory: HomeViewFactory

    private var timer: Timer? = null
    private lateinit var homeViewModel: HomeViewModel
    private var isFirstLoad = false
    private var isSliding: Boolean = false
    private var isFirstTouch: Boolean = true
    private lateinit var upcomingAdapter: UpcomingAdapter
    private lateinit var trendingAdapter: TrendingAdapter
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var discoverTvAdapter: DiscoverTvAdapter
    private lateinit var discoverMvAdapter: DiscoverMvAdapter
    private lateinit var snackbar: Snackbar
    private var currentPosition: Int = 0
    private var sizeOfHeader = 0


    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity?.application as MyApplication).getAppComponent().inject(this)
        homeViewModel = ViewModelProvider(this, homeViewFactory).get(HomeViewModel::class.java)
        binding.apply {
            vm = homeViewModel
            lifecycleOwner = this@HomeFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        binding.homeSwipe.setOnRefreshListener(this)
        snackbar = Snackbar.make(activity!!.findViewById(android.R.id.content),
            getString(R.string.detail_title_11), Snackbar.LENGTH_INDEFINITE)

        binding.header.setSearchVisibility(View.GONE)
        recyclerViewSetup()
        onClick()
        showData()
        fetchData()
        observeNetworkLoad()
        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity?.finishAffinity()
            }}
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onRefresh() {
        stopSliding()
        fetchData()
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
        upcomingAdapter = UpcomingAdapter()
        trendingAdapter = TrendingAdapter()
        discoverTvAdapter = DiscoverTvAdapter()
        discoverMvAdapter = DiscoverMvAdapter()
        genreAdapter = GenreAdapter()

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
                adapter = discoverMvAdapter
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
                snackbar.dismiss()
                val bundle = Bundle().apply {
                    putInt("filmId", item.id)
                    putString("type", Constant.MOVIE)
                }
                changeActivity(bundle, DetailActivity::class.java)
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
                snackbar.dismiss()
                stopSliding()
                val bundle = Bundle().apply {
                    putInt("filmId", item.id)
                    putString("type", Constant.MOVIE)
                }
                changeActivity(bundle, DetailActivity::class.java)
            }
        })

        genreAdapter.setGenreClickListener(object: OnAdapterListener<Genre>{
            override fun onClick(view: View, item: Genre) {
                val action = HomeFragmentDirections.actionHomeFragmentToMovieWithGenreFragment(item.id, item.name)
                Navigation.findNavController(view).navigate(action)
            }
        })

        discoverTvAdapter.setOnClickListener(object: OnAdapterListener<Tv>{
            override fun onClick(view: View, item: Tv) {
                snackbar.dismiss()
                stopSliding()
                val bundle = Bundle().apply {
                    putInt("filmId", item.id)
                    putString("type", Constant.TV)
                }
                changeActivity(bundle, DetailActivity::class.java)
            }
        })

        discoverMvAdapter.setOnClickListener(object: OnAdapterListener<Movies>{
            override fun onClick(view: View, item: Movies) {
                snackbar.dismiss()
                stopSliding()
                val bundle = Bundle().apply {
                    putInt("filmId", item.id)
                    putString("type", Constant.MOVIE)
                }
                changeActivity(bundle, DetailActivity::class.java)
            }
        })

        binding.apply {
            seeUpco.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToUpcomingFragment()
                Navigation.findNavController(it).navigate(action)
                snackbar.dismiss()
            }

            seeGenre.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToGenresFragment()
                Navigation.findNavController(it).navigate(action)
                snackbar.dismiss()
            }

            seeTv.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToTvFragment()
                Navigation.findNavController(it).navigate(action)
                snackbar.dismiss()
            }

            seeMovies.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToMoviesFragment()
                Navigation.findNavController(it).navigate(action)
                snackbar.dismiss()
            }

            noInternet.retryOnClick(View.OnClickListener { fetchData() })
        }
    }

    private fun showData(){
        homeViewModel.apply {
            getTrendingLocalData().observe(this@HomeFragment,
                Observer<List<Trending>>{ t ->
                    if(t.isNotEmpty()){
                        hideLoading()
                        sizeOfHeader = t.size
                        trendingAdapter.submitList(t)
                    }
                })

            getUpcomingLocalData().observe(this@HomeFragment,
                Observer<List<Upcoming>> { t ->
                    if(t.isNotEmpty())
                        upcomingAdapter.submitList(t)
                })

            getGenreLocalData().observe( this@HomeFragment,
                Observer<List<Genre>> { t ->
                    if(t.isNotEmpty())
                        genreAdapter.submitList(t)
                }
            )

            getDiscoverTvLocalData().observe(this@HomeFragment,
                Observer<List<Tv>> { t ->
                    if(t.isNotEmpty()){
                        discoverTvAdapter.submitList(t)
                    }
                })

            getDiscoverMvLocalData().observe(this@HomeFragment,
                Observer<List<Movies>> { t ->
                    if(t.isNotEmpty()){
                        discoverMvAdapter.submitList(t)
                    }
                })
        }
    }

    private fun fetchData(){
        lifecycleScope.launchWhenStarted {
            isFirstLoad = homeViewModel.isFirstLoad()
            homeViewModel.getDataRequest()
        }
    }

    private fun observeNetworkLoad(){
        homeViewModel.getLoadDataStatus().observe(this,
            Observer<LoadDataState>{
                when(it){
                    LoadDataState.LOADING -> {
                        if(isFirstLoad) showLoading()
                        snackbar.dismiss()
                    }
                    LoadDataState.LOADED -> hideLoading()
                    else -> {
                        binding.homeSwipe.isRefreshing = false
                        val view = snackbar.view
                        view.setBackgroundColor(getColor(context!!, R.color.colorBlackTransparent))
                        if(!isFirstLoad){
                            snackbar.setAction(getString(R.string.detail_title_12), View.OnClickListener {
                                fetchData()
                                snackbar.dismiss()
                            }).show()
                        } else networkError()
                    }
                }
            })
    }

    private fun hideLoading(){
        binding.apply {
            homeSwipe.isRefreshing = false
            shimmerHome.visibility = View.GONE
            homeLayout.visibility = View.VISIBLE
            noInternet.visibility = View.GONE
        }
    }

    private fun showLoading(){
        binding.apply {
            shimmerHome.visibility = View.VISIBLE
            homeLayout.visibility = View.GONE
            noInternet.visibility = View.GONE
        }
    }

    private fun networkError(){
        binding.apply {
            shimmerHome.visibility = View.GONE
            if(isFirstLoad){
                homeLayout.visibility = View.GONE
                noInternet.visibility = View.VISIBLE
            } else homeLayout.visibility = View.VISIBLE
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
