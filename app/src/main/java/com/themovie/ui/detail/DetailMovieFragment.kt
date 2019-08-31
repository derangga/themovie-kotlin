package com.themovie.ui.detail


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
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchDetailData
import com.themovie.restapi.ApiUrl
import com.themovie.ui.detail.adapter.CreditsAdapter
import com.themovie.ui.detail.adapter.RecommendedAdapter
import kotlinx.android.synthetic.main.fragment_detail_movie.*

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailMovieFragment : BaseFragment() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var creditsAdapter: CreditsAdapter
    private lateinit var recommendedAdapter: RecommendedAdapter
    private val tagline: String = DetailMovieFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentDetailMovieBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_detail_movie, container, false)
        val view: View = binding.root
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        binding.vm = detailViewModel
        binding.lifecycleOwner = this
        return view
    }

    override fun onMain(savedInstanceState: Bundle?) {
        initRecyclerView()
        getAllDetailData()
        observeNetworkLoad()
    }

    private fun getAllDetailData(){
        detailViewModel.getDetailMovieRequest(getBundle()!!.getInt("filmId"), ApiUrl.TOKEN).observe (
            this, Observer<FetchDetailData>{
                detailViewModel.setDetailMovieData(it.detailMovieResponse)
                creditsAdapter.submitList(it.castResponse.credits)
                recommendedAdapter.submitList(it.moviesResponse.movies)
            }
        )
    }

    private fun observeNetworkLoad(){
        detailViewModel.getLoadDataStatus().observe(this, Observer<LoadDataState>{
            if(it == LoadDataState.LOADED) {
                dt_shimmer.visibility = View.GONE
                dt_layout.visibility = View.VISIBLE
            } else
                showToastMessage("Please check your internet connection")
        })
    }

    private fun initRecyclerView(){
        creditsAdapter = CreditsAdapter()
        recommendedAdapter = RecommendedAdapter()

        dt_castList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dt_recomList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        dt_castList.adapter = creditsAdapter
        dt_recomList.adapter = recommendedAdapter
    }
}
