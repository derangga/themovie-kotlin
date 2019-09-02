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

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentDetailMovieBinding
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchDetailMovieData
import com.themovie.model.online.detail.Credits
import com.themovie.model.online.detail.Reviews
import com.themovie.model.online.discovermv.Movies
import com.themovie.restapi.ApiUrl
import com.themovie.ui.detail.adapter.CreditsAdapter
import com.themovie.ui.detail.adapter.RecommendedAdapter
import com.themovie.ui.detail.adapter.ReviewsAdapter
import com.themovie.ui.person.PersonActivity
import kotlinx.android.synthetic.main.fragment_detail_movie.*

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
    private val tagline: String = DetailMovieFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentDetailMovieBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_detail_movie, container, false)
        val view: View = binding.root
        detailMvViewModel = ViewModelProviders.of(this).get(DetailMvViewModel::class.java)
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
        detailMvViewModel.getDetailMovieRequest(getBundle()!!.getInt("filmId"), ApiUrl.TOKEN).observe (
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

        dt_castList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dt_recomList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dt_reviewList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        dt_castList.adapter = creditsAdapter
        dt_recomList.adapter = recommendedAdapter
        dt_reviewList.adapter = reviewsAdapter
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
        dt_shimmer.visibility = View.VISIBLE
        dt_layout.visibility = View.GONE
        dt_no_internet.visibility = View.GONE
    }

    private fun hideLoading(){
        dt_shimmer.visibility = View.GONE
        dt_layout.visibility = View.VISIBLE
        dt_no_internet.visibility = View.GONE
    }

    private fun showErrorConnection(){
        dt_layout.visibility = View.GONE
        dt_shimmer.visibility = View.INVISIBLE
        dt_no_internet.visibility = View.VISIBLE
    }
}
