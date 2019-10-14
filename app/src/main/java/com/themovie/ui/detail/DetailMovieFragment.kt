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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovie.MyApplication

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentDetailMovieBinding
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchDetailMovieData
import com.themovie.model.online.detail.Credits
import com.themovie.model.online.detail.Reviews
import com.themovie.model.online.discovermv.Movies
import com.themovie.ui.detail.adapter.CreditsAdapter
import com.themovie.ui.detail.adapter.RecommendedAdapter
import com.themovie.ui.detail.adapter.ReviewsAdapter
import com.themovie.ui.detail.viewmodel.DetailMovieViewModelFactory
import com.themovie.ui.detail.viewmodel.DetailMvViewModel
import com.themovie.ui.person.PersonActivity
import kotlinx.android.synthetic.main.fragment_detail_movie.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailMovieFragment : BaseFragment() {

    private lateinit var detailMvViewModel: DetailMvViewModel
    private lateinit var creditsAdapter: CreditsAdapter
    private lateinit var recommendedAdapter: RecommendedAdapter
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var binding: FragmentDetailMovieBinding

    @Inject lateinit var viewModelFactory: DetailMovieViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_detail_movie, container, false)
        val view: View = binding.root

        //val viewModelFactory = DetailViewModelFactory()
        (activity?.application as MyApplication).getAppComponent().inject(this)
        detailMvViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailMvViewModel::class.java)
        binding.vm = detailMvViewModel
        binding.lifecycleOwner = this
        return view
    }

    override fun onMain(savedInstanceState: Bundle?) {
        initRecyclerView()
        getAllDetailData()
        observeNetworkLoad()
        adapterOnClick()
    }

    private fun getAllDetailData(){
        detailMvViewModel.getDetailMovieRequest(getBundle()!!.getInt("filmId")).observe (
            this, Observer<FetchDetailMovieData>{
                detailMvViewModel.setDetailMovieData(it.detailMovieResponse)
                creditsAdapter.submitList(it.castResponse.credits)
                recommendedAdapter.submitList(it.moviesResponse.movies)
                reviewsAdapter.submitList(it.reviewResponse.reviewList)

                if(it.moviesResponse.movies.isEmpty()) dt_recom_empty.visibility = View.VISIBLE
                else dt_recom_empty.visibility = View.GONE

                if(it.reviewResponse.reviewList.isEmpty()) dt_review_empty.visibility = View.VISIBLE
                else dt_review_empty.visibility = View.GONE
            }
        )
    }

    private fun observeNetworkLoad(){
        detailMvViewModel.getLoadDataStatus().observe(this, Observer<LoadDataState>{
            if(it == LoadDataState.LOADED) {
                hideLoading()
            } else{
                showErrorConnection()
                dt_retry.setOnClickListener {
                    showLoading()
                    getAllDetailData()
                }
            }
        })
    }

    private fun initRecyclerView(){
        creditsAdapter = CreditsAdapter()
        recommendedAdapter = RecommendedAdapter()
        reviewsAdapter = ReviewsAdapter()

        binding.dtCastList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.dtRecomList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.dtReviewList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.dtCastList.adapter = creditsAdapter
        binding.dtRecomList.adapter = recommendedAdapter
        binding.dtReviewList.adapter = reviewsAdapter
    }

    private fun adapterOnClick(){
        creditsAdapter.setOnClickListener(object: CreditsAdapter.OnClickAdapterListener{
            override fun onClick(view: View?, credits: Credits) {
                val bundle = Bundle()
                bundle.putInt("person", credits.id)
                changeActivity(bundle, PersonActivity::class.java)
            }
        })

        recommendedAdapter.setOnClickListener(object: RecommendedAdapter.OnClickAdapterListener{
            override fun onClick(view: View?, movies: Movies) {
                val bundle = Bundle()
                bundle.putInt("id", movies.id)
                bundle.putString("image", movies.backdropPath.toString())
                bundle.putString("detail", Constant.MOVIE)
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
        binding.dtShimmer.visibility = View.VISIBLE
        binding.dtLayout.visibility = View.GONE
        binding.dtNoInternet.visibility = View.GONE
    }

    private fun hideLoading(){
        binding.dtShimmer.visibility = View.GONE
        binding.dtLayout.visibility = View.VISIBLE
        binding.dtNoInternet.visibility = View.GONE
    }

    private fun showErrorConnection(){
        binding.dtShimmer.visibility = View.INVISIBLE
        binding.dtLayout.visibility = View.GONE
        binding.dtNoInternet.visibility = View.VISIBLE
    }
}
