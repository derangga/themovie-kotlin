package com.themovie.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import com.themovie.model.db.*
import com.themovie.model.online.FetchMainData
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.main.adapter.*
import kotlinx.android.synthetic.main.header.*
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var homeViewFactory: HomeViewFactory

    private var timer: Timer? = null
    private lateinit var homeViewModel: HomeViewModel
    private var isUpcomingEmpty: Boolean = true
    private var isTrendingEmpty: Boolean = true
    private var isGenreEmpty: Boolean = true
    private var isDiscoverTvEmpty: Boolean = true
    private var isDiscoverMvEmpty: Boolean = true
    private var isSliding: Boolean = false
    private var isFirstTouch: Boolean = true
    private lateinit var upcomingAdapter: UpcomingAdapter
    private lateinit var trendingAdapter: TrendingAdapter
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var discoverTvAdapter: DiscoverTvAdapter
    private lateinit var discoverMvAdapter: DiscoverMvAdapter
    private lateinit var snackbar: Snackbar
    private lateinit var binding: FragmentHomeBinding
    private var currentPosition: Int = 0
    private var sizeOfHeader = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        (activity?.application as MyApplication).getAppComponent().inject(this)

        homeViewModel = ViewModelProvider(this, homeViewFactory).get(HomeViewModel::class.java)
        binding.apply {
            vm = homeViewModel
            lifecycleOwner = this@HomeFragment
        }
        return binding.root
    }

    override fun onMain(savedInstanceState: Bundle?) {
        binding.homeSwipe.setOnRefreshListener(this)
        snackbar = Snackbar.make(activity!!.findViewById(android.R.id.content),
            getString(R.string.detail_title_11), Snackbar.LENGTH_INDEFINITE)
        h_search.visibility = View.GONE
        recyclerViewSetup()
        onClick()
        fetchData()
        showData()
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

        trendingAdapter.setOnClickListener(object: TrendingAdapter.OnClickAdapterListener{
            override fun onClick(view: View?, trending: Trending, imageViewRes: ImageView) {
                snackbar.dismiss()
                val bundle = Bundle().apply {
                    putInt("filmId", trending.id)
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

        upcomingAdapter.setOnClickListener(object: UpcomingAdapter.OnClickAdapterListener{
            override fun onClick(view: View?, upcoming: Upcoming) {
                snackbar.dismiss()
                stopSliding()
                val bundle = Bundle().apply {
                    putInt("filmId", upcoming.id)
                    putString("type", Constant.MOVIE)
                }
                changeActivity(bundle, DetailActivity::class.java)
            }
        })

        genreAdapter.setGenreClickListener(object: GenreAdapter.OnClickAdapterListener{
            override fun itemGenreClick(view: View, genreLocal: Genre) {
                val action = HomeFragmentDirections.actionHomeFragmentToMovieWithGenreFragment(genreLocal.id)
                Navigation.findNavController(view).navigate(action)
            }
        })

        discoverTvAdapter.setOnClickListener(object: DiscoverTvAdapter.OnClickAdapterListener{
            override fun onClick(view: View?, tvLocal: Tv) {
                snackbar.dismiss()
                stopSliding()
                val bundle = Bundle().apply {
                    putInt("filmId", tvLocal.id)
                    putString("type", Constant.TV)
                }
                changeActivity(bundle, DetailActivity::class.java)
            }
        })

        discoverMvAdapter.setOnClickListener(object: DiscoverMvAdapter.OnClickAdapterListener {
            override fun onClick(view: View?, moviesLocal: Movies) {
                snackbar.dismiss()
                stopSliding()
                val bundle = Bundle().apply {
                    putInt("filmId", moviesLocal.id)
                    putString("type", Constant.MOVIE)
                }
                changeActivity(bundle, DetailActivity::class.java)
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

    private fun showData(){

        homeViewModel.apply {
            getTrendingLocalData().observe(this@HomeFragment,
                Observer<List<Trending>>{ t ->
                    isTrendingEmpty = t.isEmpty()
                    if(!isTrendingEmpty){
                        hideLoading()
                        sizeOfHeader = t.size
                        trendingAdapter.submitList(t)
                    }
                })

            getUpcomingLocalData().observe(this@HomeFragment,
                Observer<List<Upcoming>> { t ->
                    isUpcomingEmpty = t.isEmpty()
                    if(!isUpcomingEmpty)
                        upcomingAdapter.submitList(t)
                })

            getGenreLocalData().observe( this@HomeFragment,
                Observer<List<Genre>> { t ->
                    isGenreEmpty = t.isEmpty()
                    if(!isGenreEmpty)
                        genreAdapter.submitList(t)
                }
            )

            getDiscoverTvLocalData().observe(this@HomeFragment,
                Observer<List<Tv>> { t ->
                    isDiscoverTvEmpty = t.isEmpty()
                    if(!isDiscoverTvEmpty){
                        discoverTvAdapter.submitList(t)
                        //disctv_card.visibility = View.VISIBLE
                    }
                })

            getDiscoverMvLocalData().observe(this@HomeFragment,
                Observer<List<Movies>> { t ->
                    isDiscoverMvEmpty = t.isEmpty()
                    if(!isDiscoverMvEmpty){
                        discoverMvAdapter.submitList(t)
                        //discmv_card.visibility = View.VISIBLE
                    }
                })
        }


    }

    private fun fetchData(){
        homeViewModel.getDataRequest().observe(this,
            Observer<FetchMainData> { t ->
                homeViewModel.apply {
                    if(isTrendingEmpty && isUpcomingEmpty && isGenreEmpty && isDiscoverTvEmpty && isDiscoverMvEmpty){
                        insertLocalTrending(t.popular!!.results)
                        insertLocalUpcoming(t.upcomingResponse!!.results)
                        insertLocalGenre(t.genre!!.genres)
                        insertLocalTv(t.tvResponse!!.results)
                        insertLocalMovies(t.moviesResponse!!.movies)
                    } else {
                        updateLocalTrending(t?.popular!!.results)
                        updateLocalUpComing(t.upcomingResponse!!.results)
                        updateLocalGenre(t.genre!!.genres)
                        updateLocalTv(t.tvResponse!!.results)
                        updateLocalMovies(t.moviesResponse!!.movies)
                    }
                    hideLoading()
                }
            })
    }

    private fun observeNetworkLoad(){
        homeViewModel.getLoadDataStatus().observe(this,
            Observer<LoadDataState>{
                if(it == LoadDataState.ERROR) {
                    binding.homeSwipe.isRefreshing = false
                    val view = snackbar.view
                    view.setBackgroundColor(getColor(context!!, R.color.colorBlackTransparent))
                    snackbar.setAction(getString(R.string.detail_title_12), View.OnClickListener {
                        fetchData()
                        snackbar.dismiss()
                    }).show()
                }
            })
    }

    private fun hideLoading(){
        binding.apply {
            homeSwipe.isRefreshing = false
            shimmerHome.visibility = View.GONE
            homeLayout.visibility = View.VISIBLE
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
