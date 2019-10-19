package com.themovie.ui.detail


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovie.MyApplication

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentDetailTvBinding
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchDetailTvData
import com.themovie.model.online.detail.Credits
import com.themovie.model.online.detail.Reviews
import com.themovie.model.online.discovertv.Tv
import com.themovie.restapi.ApiUrl
import com.themovie.ui.detail.adapter.CreditsAdapter
import com.themovie.ui.detail.adapter.RecommendedTvAdapter
import com.themovie.ui.detail.adapter.ReviewsAdapter
import com.themovie.ui.detail.adapter.SeasonAdapter
import com.themovie.ui.detail.viewmodel.DetailTvViewModel
import com.themovie.ui.detail.viewmodel.DetailTvViewModelFactory
import com.themovie.ui.person.PersonActivity
import kotlinx.android.synthetic.main.fragment_detail_tv.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 *
 */
class DetailTvFragment : BaseFragment() {

    @Inject lateinit var viewModelFactory: DetailTvViewModelFactory
    private lateinit var detailTvViewModel: DetailTvViewModel
    private lateinit var seasonAdapter: SeasonAdapter
    private lateinit var creditsAdapter: CreditsAdapter
    private lateinit var recommendedTvAdapter: RecommendedTvAdapter
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var binding: FragmentDetailTvBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_detail_tv, container, false)
        val view: View = binding.root
        (activity?.application as MyApplication).getAppComponent().inject(this)
        detailTvViewModel = ViewModelProvider(this, viewModelFactory).get(DetailTvViewModel::class.java)
        binding.apply {
            vm = detailTvViewModel
            lifecycleOwner = this@DetailTvFragment
        }


        return view
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

        binding.apply {
            dtSeasonList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            dtCastList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            dtRecomList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            dtReviewList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            dtSeasonList.adapter = seasonAdapter
            dtCastList.adapter = creditsAdapter
            dtRecomList.adapter = recommendedTvAdapter
            dtReviewList.adapter = reviewsAdapter
        }

    }

    private fun getAllDetailData(){
        detailTvViewModel.getDetailTvRequest(getBundle()!!.getInt("filmId")).observe(
            this, Observer<FetchDetailTvData> {
                detailTvViewModel.setDetailTvData(it.detailTvResponse!!)
                seasonAdapter.submitList(it.detailTvResponse.seasons)
                creditsAdapter.submitList(it.castResponse?.credits)
                recommendedTvAdapter.submitList(it.tvResponse?.results)
                reviewsAdapter.submitList(it.reviews?.reviewList)

                if(it.tvResponse?.results.isNullOrEmpty()) binding.dtRecomEmpty.visibility = View.VISIBLE
                else binding.dtRecomEmpty.visibility = View.GONE

                if(it.castResponse?.credits.isNullOrEmpty()) binding.dtCastEmpty.visibility = View.VISIBLE
                else binding.dtCastEmpty.visibility = View.GONE

                if(it.reviews?.reviewList.isNullOrEmpty()) binding.dtReviewEmpty.visibility = View.VISIBLE
                else binding.dtReviewEmpty.visibility = View.GONE
            }
        )
    }

    private fun observeLoadData(){
        detailTvViewModel.getLoadDataStatus().observe( this,
            Observer<LoadDataState> {
                if(it == LoadDataState.LOADED) hideLoading()
                else {
                    showErrorConnection()
                    binding.dtRetry.setOnClickListener {
                        showLoading()
                        getAllDetailData()
                    }
                }
            }
        )
    }

    private fun adapterOnCLick(){

        creditsAdapter.setOnClickListener(object: CreditsAdapter.OnClickAdapterListener{
            override fun onClick(credits: Credits) {
                val bundle = Bundle()
                bundle.putInt("person", credits.id)
                changeActivity(bundle, PersonActivity::class.java)
            }
        })

        recommendedTvAdapter.setOnClickListener(object: RecommendedTvAdapter.OnClickAdapterListener{
            override fun onClick(view: View?, tv: Tv) {
                val bundle = Bundle().apply {
                    putInt("id", tv.id)
                    putString("image", tv.backdropPath.toString())
                    putString("detail", Constant.TV)
                }

                changeActivity(bundle, DetailActivity::class.java)
            }
        })

        reviewsAdapter.setOnClickListener(object: ReviewsAdapter.OnClickAdapterListener{
            override fun onClick(view: View?, reviews: Reviews) {
                val uri: Uri = Uri.parse(reviews.url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context?.startActivity(intent)
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
