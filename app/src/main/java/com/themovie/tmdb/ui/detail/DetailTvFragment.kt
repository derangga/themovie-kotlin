package com.themovie.tmdb.ui.detail


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.themovie.core.page.BaseFragment
import com.themovie.core.event.EventObserver
import com.themovie.core.utils.changeActivity
import com.themovie.core.utils.gone
import com.themovie.core.utils.navigateFragment
import com.themovie.core.utils.visible
import com.themovie.datasource.entities.ui.Credit
import com.themovie.datasource.entities.ui.Review
import com.themovie.datasource.entities.ui.Tv
import com.themovie.datasource.entities.ui.Video

import com.themovie.tmdb.R
import com.themovie.tmdb.databinding.FragmentDetailTvBinding
import com.themovie.tmdb.helper.Constant

import com.themovie.tmdb.ui.detail.adapter.CreditsAdapter
import com.themovie.tmdb.ui.detail.adapter.RecommendedTvAdapter
import com.themovie.tmdb.ui.detail.adapter.ReviewsAdapter
import com.themovie.tmdb.ui.detail.adapter.SeasonAdapter
import com.themovie.tmdb.ui.detail.adapter.VideoAdapter
import com.themovie.tmdb.ui.detail.viewmodel.DetailTvViewModel
import com.themovie.tmdb.ui.youtube.YoutubeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTvFragment : BaseFragment<FragmentDetailTvBinding>() {

    private val viewModel by activityViewModels<DetailTvViewModel>()
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

        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun onMain(savedInstanceState: Bundle?) {
        setupRecycler()
        viewModel.loading.value?.takeIf { it }?.run {
            viewModel.getDetailTvRequest(filmId)
        }
        subscribeUI()
    }

    private fun setupRecycler(){
        binding.apply {
            dtSeasonList.adapter = seasonAdapter
            dtCastList.adapter = creditsAdapter
            dtRecomList.adapter = recommendedTvAdapter
            dtReviewList.adapter = reviewsAdapter
            dtVideoList.adapter = videoAdapter
        }

    }

    private fun subscribeUI(){

        viewModel.eventError.observe(viewLifecycleOwner, EventObserver {
            if(it) networkErrorDialog.show(childFragmentManager, "")
        })

        viewModel.detailTvRes.observe(viewLifecycleOwner, {
            binding.tv = it
            seasonAdapter.submitList(it.seasonResponses)
        })
        viewModel.creditMovieRes.observe(viewLifecycleOwner, { credits ->
            if (credits.isEmpty()) binding.dtCastEmpty.visible()
            else binding.dtCastEmpty.gone()
            creditsAdapter.submitList(credits)
        })

        viewModel.recommendationTvRes.observe(viewLifecycleOwner, { tv ->
            if(tv.isEmpty()) binding.dtRecomEmpty.visible()
            else binding.dtRecomEmpty.gone()
            recommendedTvAdapter.submitList(tv)
        })

        viewModel.trailerTvRes.observe(viewLifecycleOwner, { trailer ->
            if(trailer.isEmpty()) binding.videoEmpty.visible()
            else binding.videoEmpty.gone()
            videoAdapter.submitList(trailer)
        })

        viewModel.reviewsTvRes.observe(viewLifecycleOwner, { reviews ->
            if(reviews.isEmpty()) binding.dtReviewEmpty.visible()
            else binding.dtReviewEmpty.gone()
            reviewsAdapter.submitList(reviews)
        })
    }

    private fun onClickCreditItem(credit: Credit) {
        val action = DetailTvFragmentDirections.actionDetailTvFragmentToPersonFragment2(credit.id)
        view.navigateFragment { Navigation.findNavController(it).navigate(action) }
    }

    private fun onClickRecommendationItem(tv: Tv) {
        val bundle = Bundle().apply {
            putInt("filmId", tv.id)
            putString("type", Constant.TV)
        }
        changeActivity<DetailActivity>(bundle)
    }

    private fun onClickVideoTrailerItem(video: Video) {
        val bundle = Bundle().apply {
            putString("key", video.key)
        }
        changeActivity<YoutubeActivity>(bundle)
    }

    private fun onClickReviewItem(review: Review) {
        val uri: Uri = Uri.parse(review.url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        requireContext().startActivity(intent)
    }

    override fun delegateRetryEventDialog() {
        viewModel.getDetailTvRequest(filmId)
    }
}
