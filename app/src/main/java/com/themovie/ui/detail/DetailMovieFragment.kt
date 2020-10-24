package com.themovie.ui.detail


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.Result.Status.*
import com.aldebaran.domain.entities.remote.Credits
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.entities.remote.ReviewsResponse
import com.aldebaran.domain.entities.remote.Videos
import com.aldebaran.core.BaseFragment
import com.aldebaran.utils.*

import com.themovie.R
import com.themovie.databinding.FragmentDetailMovieBinding
import com.themovie.helper.Constant
import com.themovie.ui.detail.adapter.CreditsAdapter
import com.themovie.ui.detail.adapter.RecommendedAdapter
import com.themovie.ui.detail.adapter.ReviewsAdapter
import com.themovie.ui.detail.adapter.VideoAdapter
import com.themovie.ui.detail.viewmodel.DetailMovieViewModel
import com.themovie.ui.youtube.YoutubeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : BaseFragment<FragmentDetailMovieBinding>() {

    private val viewModel by viewModels<DetailMovieViewModel>()
    private val creditsAdapter by lazy { CreditsAdapter(::onClickCreditItem) }
    private val recommendedAdapter by lazy { RecommendedAdapter(::onClickRecommendationItem) }
    private val reviewsAdapter by lazy { ReviewsAdapter(::onClickReviewItem) }
    private val videoAdapter by lazy { VideoAdapter(::onClickVideoTrailerItem) }

    private var filmId = 0

    override fun getLayout(): Int {
        return R.layout.fragment_detail_movie
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        arguments?.let {
            filmId = DetailMovieFragmentArgs.fromBundle(it).filmId
        }

        binding.lifecycleOwner = this@DetailMovieFragment

    }

    override fun onMain(savedInstanceState: Bundle?) {
        initRecyclerView()
        subscribeUI()
        viewModel.getDetailMovieRequest(filmId)
    }

    private fun subscribeUI(){
        viewModel.apply {
            detailMovieRes.observe(viewLifecycleOwner, { res ->
                when(res.status){
                    SUCCESS -> {
                        hideLoading()
                        binding.movies = res?.data
                    }
                    ERROR -> {
                        networkErrorDialog.show(childFragmentManager, "")
                    }
                    LOADING -> { showLoading() }
                }
            })

            creditMovieRes.observe(viewLifecycleOwner, { res ->
                when(res.status){
                    SUCCESS -> {
                        if(res.data?.credits.isNullOrEmpty()) binding.dtCastEmpty.visible()
                        else binding.dtCastEmpty.gone()
                        creditsAdapter.submitList(res.data?.credits)
                    }
                    else -> {}
                }
            })

            recommendationMovieRes.observe(viewLifecycleOwner, { res ->
                when(res.status){
                    SUCCESS -> {
                        if(res.data?.results.isNullOrEmpty()) binding.dtRecomEmpty.visible()
                        else binding.dtRecomEmpty.gone()
                        recommendedAdapter.submitList(res.data?.results)
                    }
                    else -> {}
                }
            })

            trailerMovieRes.observe(viewLifecycleOwner, { res ->
                when(res.status){
                    SUCCESS -> {
                        if(res.data?.videos.isNullOrEmpty()) binding.videoEmpty.visible()
                        else binding.videoEmpty.gone()
                        videoAdapter.submitList(res.data?.videos)
                    }
                    else -> {}
                }
            })

            reviewsMovieRes.observe(viewLifecycleOwner, { res ->
                when(res.status){
                    SUCCESS -> {
                        if(res.data?.results.isNullOrEmpty()) binding.dtReviewEmpty.visible()
                        else binding.dtReviewEmpty.gone()
                        reviewsAdapter.submitList(res.data?.results)
                    }
                    else -> {}
                }
            })
        }
    }

    private fun initRecyclerView(){
        binding.apply {
            dtCastList.initLinearRecycler(requireContext(), RecyclerView.HORIZONTAL)
            dtRecomList.initLinearRecycler(requireContext(), RecyclerView.HORIZONTAL)
            dtVideoList.initLinearRecycler(requireContext(), RecyclerView.HORIZONTAL)
            dtReviewList.initLinearRecycler(requireContext())

            dtCastList.adapter = creditsAdapter
            dtRecomList.adapter = recommendedAdapter
            dtReviewList.adapter = reviewsAdapter
            dtVideoList.adapter = videoAdapter
        }
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

    private fun onClickCreditItem(credit: Credits) {
        val action = DetailMovieFragmentDirections.actionDetailMovieFragmentToPersonFragment(credit.id ?: 0)
        view.navigateFragment { Navigation.findNavController(it).navigate(action) }
    }

    private fun onClickRecommendationItem(movie: MovieResponse) {
        val bundle = Bundle().apply {
            putInt("filmId", movie.id ?: 0)
            putString("type", Constant.MOVIE)
        }
        changeActivity<DetailActivity>(bundle)
    }

    private fun onClickVideoTrailerItem(video: Videos) {
        val bundle = Bundle().apply {
            putString("key", video.key)
        }
        changeActivity<YoutubeActivity>(bundle)
    }

    private fun onClickReviewItem(review: ReviewsResponse) {
        val uri: Uri = Uri.parse(review.url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        requireContext().startActivity(intent)
    }

    override fun delegateRetryEventDialog() {
        viewModel.getDetailMovieRequest(filmId)
    }
}
