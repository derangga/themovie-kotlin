package com.themovie.ui.detail


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentDetailTvBinding
import com.themovie.di.detail.DetailViewModelFactory
import com.themovie.helper.*
import com.themovie.model.online.detail.Credits
import com.themovie.model.online.detail.Reviews
import com.themovie.model.db.Tv
import com.themovie.model.online.video.Videos
import com.themovie.ui.detail.adapter.*
import com.themovie.ui.detail.viewmodel.DetailTvViewModel
import com.themovie.ui.youtube.YoutubeActivity
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class DetailTvFragment : BaseFragment<FragmentDetailTvBinding>() {

    @Inject lateinit var factory: DetailViewModelFactory
    private val viewModel by viewModels<DetailTvViewModel> { factory }
    private lateinit var seasonAdapter: SeasonAdapter
    private lateinit var creditsAdapter: CreditsAdapter
    private lateinit var recommendedTvAdapter: RecommendedTvAdapter
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var videoAdapter: VideoAdapter
    private var filmId = 0

    override fun getLayout(): Int {
        return R.layout.fragment_detail_tv
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity as DetailActivity).getDetailComponent().inject(this)
        arguments?.let {
            filmId = DetailTvFragmentArgs.fromBundle(it).filmId
        }

        binding.apply {
            lifecycleOwner = this@DetailTvFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        getAllDetailData()
        observeLoadData()
        setupRecycer()
        adapterOnCLick()
    }

    private fun setupRecycer(){
        seasonAdapter = SeasonAdapter()
        creditsAdapter = CreditsAdapter()
        recommendedTvAdapter = RecommendedTvAdapter()
        reviewsAdapter = ReviewsAdapter()
        videoAdapter = VideoAdapter()

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

    private fun getAllDetailData(){
        viewModel.getDetailTvRequest(filmId)
        viewModel.setDetailTv().observe(
            viewLifecycleOwner, Observer {
                binding.tv = it.detailTvResponse
                seasonAdapter.submitList(it.detailTvResponse?.seasons)
                creditsAdapter.submitList(it.castResponse?.credits)
                recommendedTvAdapter.submitList(it.tvResponse?.results)
                reviewsAdapter.submitList(it.reviews?.reviewList)
                videoAdapter.submitList(it.videoResponse?.videos)

                if(it.tvResponse?.results.isNullOrEmpty()) binding.dtRecomEmpty.visible()
                else binding.dtRecomEmpty.gone()

                if(it.castResponse?.credits.isNullOrEmpty()) binding.dtCastEmpty.visible()
                else binding.dtCastEmpty.gone()

                if(it.videoResponse?.videos.isNullOrEmpty()) binding.videoEmpty.visible()
                else binding.videoEmpty.gone()

                if(it.reviews?.reviewList.isNullOrEmpty()) binding.dtReviewEmpty.visible()
                else binding.dtReviewEmpty.gone()
            }
        )
    }

    private fun observeLoadData(){
        viewModel.getLoadStatus().observe( viewLifecycleOwner,
            Observer {
                when (it) {
                    LoadDataState.LOADING -> showLoading()
                    LoadDataState.LOADED -> hideLoading()
                    else -> {
                        showErrorConnection()
                        binding.dtNoInternet.retryOnClick(View.OnClickListener {
                            showLoading()
                            viewModel.getDetailTvRequest(filmId)
                        })
                    }
                }
            }
        )
    }

    private fun adapterOnCLick(){

        creditsAdapter.setOnClickListener(object: OnAdapterListener<Credits>{
            override fun onClick(view: View, item: Credits) {
                val action = DetailTvFragmentDirections.actionDetailTvFragmentToPersonFragment2(item.id)
                Navigation.findNavController(view).navigate(action)
            }
        })

        recommendedTvAdapter.setOnClickListener(object: OnAdapterListener<Tv>{
            override fun onClick(view: View, item: Tv) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id)
                    putString("type", Constant.TV)
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
            dtShimmer.visible()
            dtLayout.gone()
            dtNoInternet.gone()
        }
    }

    private fun hideLoading(){
        binding.apply {
            dtShimmer.gone()
            dtLayout.visible()
            dtNoInternet.gone()
        }
    }

    private fun showErrorConnection(){
        binding.apply {
            dtShimmer.invisible()
            dtLayout.gone()
            dtNoInternet.visible()
        }
    }

}
