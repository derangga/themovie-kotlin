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
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.entities.ui.Review
import com.themovie.datasource.entities.ui.Video

import com.themovie.tmdb.R
import com.themovie.tmdb.databinding.FragmentDetailMovieBinding
import com.themovie.tmdb.helper.Constant
import com.themovie.tmdb.ui.detail.adapter.CreditsAdapter
import com.themovie.tmdb.ui.detail.adapter.RecommendedAdapter
import com.themovie.tmdb.ui.detail.adapter.ReviewsAdapter
import com.themovie.tmdb.ui.detail.adapter.VideoAdapter
import com.themovie.tmdb.ui.detail.viewmodel.DetailMovieViewModel
import com.themovie.tmdb.ui.youtube.YoutubeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : BaseFragment<FragmentDetailMovieBinding>() {

    private val viewModel by activityViewModels<DetailMovieViewModel>()
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
        binding.vm = viewModel
        binding.lifecycleOwner = this@DetailMovieFragment

    }

    override fun onMain(savedInstanceState: Bundle?) {
        initRecyclerView()
        viewModel.loading.value?.takeIf { it }?.run {
            viewModel.getDetailMovieRequest(filmId)
        }
        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.eventError.observe(viewLifecycleOwner, EventObserver {
            if (it) networkErrorDialog.show(childFragmentManager, "")
        })

        viewModel.detailMovieRes.observe(viewLifecycleOwner, { binding.movies = it })
        viewModel.creditMovieRes.observe(viewLifecycleOwner, { credits ->
            if (credits.isEmpty()) binding.dtCastEmpty.visible()
            else binding.dtCastEmpty.gone()
            creditsAdapter.submitList(credits)
        })

        viewModel.trailerMovieRes.observe(viewLifecycleOwner, { trailer ->
            if(trailer.isEmpty()) binding.videoEmpty.visible()
            else binding.videoEmpty.gone()
            videoAdapter.submitList(trailer)
        })

        viewModel.recommendationMovieRes.observe(viewLifecycleOwner, { movie ->
            if(movie.isEmpty()) binding.dtRecomEmpty.visible()
            else binding.dtRecomEmpty.gone()
            recommendedAdapter.submitList(movie)
        })

        viewModel.reviewsMovieRes.observe(viewLifecycleOwner, { reviews ->
            if(reviews.isEmpty()) binding.dtReviewEmpty.visible()
            else binding.dtReviewEmpty.gone()
            reviewsAdapter.submitList(reviews)
        })
    }

    private fun initRecyclerView() {
        binding.apply {
            dtCastList.adapter = creditsAdapter
            dtRecomList.adapter = recommendedAdapter
            dtReviewList.adapter = reviewsAdapter
            dtVideoList.adapter = videoAdapter
        }
    }

    private fun onClickCreditItem(credit: Credit) {
        val action =
            DetailMovieFragmentDirections.actionDetailMovieFragmentToPersonFragment(credit.id)
        view.navigateFragment { Navigation.findNavController(it).navigate(action) }
    }

    private fun onClickRecommendationItem(movie: Movie) {
        val bundle = Bundle().apply {
            putInt("filmId", movie.id)
            putString("type", Constant.MOVIE)
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
        viewModel.getDetailMovieRequest(filmId)
    }
}
