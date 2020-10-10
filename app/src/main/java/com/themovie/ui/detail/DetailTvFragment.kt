package com.themovie.ui.detail


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldebaran.domain.Result.Status.*
import com.aldebaran.base.BaseFragment
import com.aldebaran.domain.entities.remote.*
import com.aldebaran.utils.changeActivity
import com.aldebaran.utils.gone
import com.aldebaran.utils.navigateFragment
import com.aldebaran.utils.visible

import com.themovie.R
import com.themovie.databinding.FragmentDetailTvBinding
import com.themovie.helper.Constant
import com.themovie.helper.showNetworkError

import com.themovie.ui.detail.adapter.*
import com.themovie.ui.detail.viewmodel.DetailTvViewModel
import com.themovie.ui.youtube.YoutubeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTvFragment : BaseFragment<FragmentDetailTvBinding>() {

    private val viewModel by viewModels<DetailTvViewModel>()
    private val seasonAdapter by lazy { SeasonAdapter() }
    private val creditsAdapter by lazy { CreditsAdapter(::onClickCreditItem) }
    private val recommendedTvAdapter by lazy { RecommendedTvAdapter(::onClickRecommendationItem) }
    private val reviewsAdapter by lazy { ReviewsAdapter(::onClickReviewItem) }
    private val videoAdapter by lazy { VideoAdapter(::onClickVideoTrailerItem) }
    private var filmId = 0

    override fun getLayout(): Int {
        return R.layout.fragment_detail_tv
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        arguments?.let {
            filmId = DetailTvFragmentArgs.fromBundle(it).filmId
        }

        binding.apply {
            lifecycleOwner = this@DetailTvFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        setupRecycler()
        subscribeUI()
        viewModel.getDetailTvRequest(filmId)
    }

    private fun setupRecycler(){

        binding.apply {
            dtSeasonList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            dtCastList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            dtRecomList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            dtReviewList.layoutManager = LinearLayoutManager(context)
            dtVideoList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            dtSeasonList.adapter = seasonAdapter
            dtCastList.adapter = creditsAdapter
            dtRecomList.adapter = recommendedTvAdapter
            dtReviewList.adapter = reviewsAdapter
            dtVideoList.adapter = videoAdapter
        }

    }

    private fun subscribeUI(){
        viewModel.apply {
            detailTvRes.observe(viewLifecycleOwner, { res ->
                when(res.status){
                    SUCCESS -> {
                        hideLoading()
                        binding.tv = res?.data
                        seasonAdapter.submitList(res?.data?.seasons)
                    }
                    ERROR -> {
                        showNetworkError(false){
                            viewModel.getDetailTvRequest(filmId)
                        }
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

            recommendationTvRes.observe(viewLifecycleOwner, { res ->
                when(res.status){
                    SUCCESS -> {
                        if(res.data?.results.isNullOrEmpty()) binding.dtRecomEmpty.visible()
                        else binding.dtRecomEmpty.gone()
                        recommendedTvAdapter.submitList(res.data?.results)
                    }
                    else -> {}
                }
            })

            trailerTvRes.observe(viewLifecycleOwner, { res ->
                when(res.status){
                    SUCCESS -> {
                        if(res.data?.videos.isNullOrEmpty()) binding.videoEmpty.visible()
                        else binding.videoEmpty.gone()
                        videoAdapter.submitList(res.data?.videos)
                    }
                    else -> {}
                }
            })

            reviewsTvRes.observe(viewLifecycleOwner, { res ->
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
        val action = DetailTvFragmentDirections.actionDetailTvFragmentToPersonFragment2(credit.id ?: 0)
        view.navigateFragment { Navigation.findNavController(it).navigate(action) }
    }

    private fun onClickRecommendationItem(tv: TvResponse) {
        val bundle = Bundle().apply {
            putInt("filmId", tv.id ?: 0)
            putString("type", Constant.TV)
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
}
