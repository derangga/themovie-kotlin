package com.themovie.ui.detail


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovie.MyApplication

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentDetailMovieBinding
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.helper.OnAdapterListener
import com.themovie.model.online.FetchDetailMovieData
import com.themovie.model.online.detail.Credits
import com.themovie.model.online.detail.Reviews
import com.themovie.model.db.Movies
import com.themovie.model.online.video.Videos
import com.themovie.ui.detail.adapter.CreditsAdapter
import com.themovie.ui.detail.adapter.RecommendedAdapter
import com.themovie.ui.detail.adapter.ReviewsAdapter
import com.themovie.ui.detail.adapter.VideoAdapter
import com.themovie.ui.detail.viewmodel.DetailMovieViewModelFactory
import com.themovie.ui.detail.viewmodel.DetailMvViewModel
import com.themovie.ui.youtube.YoutubeActivity
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailMovieFragment : BaseFragment<FragmentDetailMovieBinding>() {

    private lateinit var detailMvViewModel: DetailMvViewModel
    private lateinit var creditsAdapter: CreditsAdapter
    private lateinit var recommendedAdapter: RecommendedAdapter
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var videoAdapter: VideoAdapter

    @Inject lateinit var viewModelFactory: DetailMovieViewModelFactory

    override fun getLayout(): Int {
        return R.layout.fragment_detail_movie
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity?.application as MyApplication).getAppComponent().inject(this)
        arguments?.let {
            val filmId = DetailMovieFragmentArgs.fromBundle(it).filmId
            DetailMvViewModel.setFilmId(filmId)
        }
        detailMvViewModel = ViewModelProvider(this, viewModelFactory).get(DetailMvViewModel::class.java)
        binding.apply {
            lifecycleOwner = this@DetailMovieFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        initRecyclerView()
        getAllDetailData()
        observeNetworkLoad()
        adapterOnClick()
    }

    private fun getAllDetailData(){
        detailMvViewModel.getDetailMovieRequest()
        detailMvViewModel.setDetailMovie().observe (
            this, Observer<FetchDetailMovieData>{
                binding.movies = it.detailMovieResponse
                creditsAdapter.submitList(it.castResponse?.credits)
                recommendedAdapter.submitList(it.moviesResponse?.movies)
                reviewsAdapter.submitList(it.reviewResponse?.reviewList)
                videoAdapter.submitList(it.videoResponse?.videos)

                if(it.moviesResponse?.movies.isNullOrEmpty()) binding.dtRecomEmpty.visibility = View.VISIBLE
                else binding.dtRecomEmpty.visibility = View.GONE

                if(it.castResponse?.credits.isNullOrEmpty()) binding.dtCastEmpty.visibility = View.VISIBLE
                else binding.dtCastEmpty.visibility = View.GONE

                if(it.videoResponse?.videos.isNullOrEmpty()) binding.videoEmpty.visibility = View.VISIBLE
                else binding.videoEmpty.visibility = View.GONE

                if(it.reviewResponse?.reviewList.isNullOrEmpty()) binding.dtReviewEmpty.visibility = View.VISIBLE
                else binding.dtReviewEmpty.visibility = View.GONE
            }
        )
    }

    private fun observeNetworkLoad(){
        detailMvViewModel.getLoadStatus().observe(this, Observer<LoadDataState>{
            when (it) {
                LoadDataState.LOADED -> hideLoading()
                else -> {
                    showErrorConnection()
                    binding.dtNoInternet.retryOnClick(View.OnClickListener {
                        showLoading()
                        detailMvViewModel.getDetailMovieRequest()
                    })
                }
            }
        })
    }

    private fun initRecyclerView(){
        creditsAdapter = CreditsAdapter()
        recommendedAdapter = RecommendedAdapter()
        reviewsAdapter = ReviewsAdapter()
        videoAdapter = VideoAdapter()

        binding.apply {
            dtCastList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            dtRecomList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            dtReviewList.layoutManager = LinearLayoutManager(context)
            dtVideoList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            dtCastList.adapter = creditsAdapter
            dtRecomList.adapter = recommendedAdapter
            dtReviewList.adapter = reviewsAdapter
            dtVideoList.adapter = videoAdapter
        }


    }

    private fun adapterOnClick(){
        creditsAdapter.setOnClickListener(object: OnAdapterListener<Credits>{
            override fun onClick(view: View, item: Credits) {
                val action = DetailMovieFragmentDirections.actionDetailMovieFragmentToPersonFragment(item.id)
                Navigation.findNavController(view).navigate(action)
            }
        })

        recommendedAdapter.setOnClickListener(object: OnAdapterListener<Movies>{
            override fun onClick(view: View, item: Movies) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id)
                    putString("type", Constant.MOVIE)
                }
                changeActivity(bundle, DetailActivity::class.java)
            }
        })

        reviewsAdapter.setOnClickListener(object: OnAdapterListener<Reviews>{
            override fun onClick(view: View, item: Reviews) {
                val uri: Uri = Uri.parse(item.url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context?.startActivity(intent)
            }
        })

        videoAdapter.setOnClickAdapter(object: OnAdapterListener<Videos>{
            override fun onClick(view: View, item: Videos) {
                val bundle = Bundle()
                bundle.putString("key", item.key)
                changeActivity(bundle, YoutubeActivity::class.java)
            }
        })
    }

    private fun showLoading(){
        binding.apply {
            dtShimmer.visibility = View.VISIBLE
            dtLayout.visibility = View.GONE
            dtNoInternet.visibility = View.GONE
        }

    }

    private fun hideLoading(){
        binding.apply {
            dtShimmer.visibility = View.GONE
            dtLayout.visibility = View.VISIBLE
            dtNoInternet.visibility = View.GONE
        }

    }

    private fun showErrorConnection(){
        binding.apply {
            dtShimmer.visibility = View.INVISIBLE
            dtLayout.visibility = View.GONE
            dtNoInternet.visibility = View.VISIBLE
        }

    }
}
