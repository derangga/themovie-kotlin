package com.themovie.ui.detail


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentDetailMovieBinding
import com.themovie.di.detail.DetailViewModelFactory
import com.themovie.helper.*
import com.themovie.model.online.detail.Credits
import com.themovie.model.online.detail.Reviews
import com.themovie.model.db.Movies
import com.themovie.model.online.video.Videos
import com.themovie.restapi.Result
import com.themovie.restapi.Result.Status.*
import com.themovie.ui.detail.adapter.CreditsAdapter
import com.themovie.ui.detail.adapter.RecommendedAdapter
import com.themovie.ui.detail.adapter.ReviewsAdapter
import com.themovie.ui.detail.adapter.VideoAdapter
import com.themovie.ui.detail.viewmodel.DetailMovieViewModel
import com.themovie.ui.youtube.YoutubeActivity
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailMovieFragment : BaseFragment<FragmentDetailMovieBinding>() {
    
    @Inject lateinit var factory: DetailViewModelFactory
    private val viewModel by viewModels<DetailMovieViewModel> { factory }
    private lateinit var creditsAdapter: CreditsAdapter
    private lateinit var recommendedAdapter: RecommendedAdapter
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var videoAdapter: VideoAdapter
    private var filmId = 0

    override fun getLayout(): Int {
        return R.layout.fragment_detail_movie
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity as DetailActivity).getDetailComponent().inject(this)
        arguments?.let {
            filmId = DetailMovieFragmentArgs.fromBundle(it).filmId
        }

        binding.lifecycleOwner = this@DetailMovieFragment

    }

    override fun onMain(savedInstanceState: Bundle?) {
        initRecyclerView()
        adapterOnClick()
        subscribeUI()
        viewModel.getDetailMovieRequest(filmId)
    }

    private fun subscribeUI(){
        viewModel.apply {
            detailMovieRes.observe(viewLifecycleOwner, Observer { res ->
                when(res.status){
                    SUCCESS -> {
                        hideLoading()
                        binding.movies = res?.data
                    }
                    ERROR -> {
                        showNetworkError(false){
                            viewModel.getDetailMovieRequest(filmId)
                        }
                    }
                    LOADING -> { showLoading() }
                }
            })

            creditMovieRes.observe(viewLifecycleOwner, Observer { res ->
                when(res.status){
                    SUCCESS -> {
                        if(res.data?.credits.isNullOrEmpty()) binding.dtCastEmpty.visible()
                        else binding.dtCastEmpty.gone()
                        creditsAdapter.submitList(res.data?.credits)
                    }
                    else -> {}
                }
            })

            recommendationMovieRes.observe(viewLifecycleOwner, Observer { res ->
                when(res.status){
                    SUCCESS -> {
                        if(res.data?.movies.isNullOrEmpty()) binding.dtRecomEmpty.visible()
                        else binding.dtRecomEmpty.gone()
                        recommendedAdapter.submitList(res.data?.movies)
                    }
                    else -> {}
                }
            })

            trailerMovieRes.observe(viewLifecycleOwner, Observer { res ->
                when(res.status){
                    SUCCESS -> {
                        if(res.data?.videos.isNullOrEmpty()) binding.videoEmpty.visible()
                        else binding.videoEmpty.gone()
                        videoAdapter.submitList(res.data?.videos)
                    }
                    else -> {}
                }
            })

            reviewsMovieRes.observe(viewLifecycleOwner, Observer { res ->
                when(res.status){
                    SUCCESS -> {
                        if(res.data?.reviewList.isNullOrEmpty()) binding.dtReviewEmpty.visible()
                        else binding.dtReviewEmpty.gone()
                        reviewsAdapter.submitList(res.data?.reviewList)
                    }
                    else -> {}
                }
            })
        }
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
                val action = DetailMovieFragmentDirections.actionDetailMovieFragmentToPersonFragment(item.id ?: 0)
                Navigation.findNavController(view).navigate(action)
            }
        })

        recommendedAdapter.setOnClickListener(object: OnAdapterListener<Movies>{
            override fun onClick(view: View, item: Movies) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id ?: 0)
                    putString("type", Constant.MOVIE)
                }
                changeActivity<DetailActivity>(bundle)
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
                Intent(context, YoutubeActivity::class.java)
                    .also { intent ->
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
            }
        })
    }

    private fun showLoading(){
        binding.apply {
            dtShimmer.visible()
            dtLayout.gone()
        }

    }

    private fun hideLoading(){
        binding.apply {
            dtShimmer.gone()
            dtLayout.visible()
        }

    }
}
