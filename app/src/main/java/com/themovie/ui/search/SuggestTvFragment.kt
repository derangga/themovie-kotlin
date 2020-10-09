package com.themovie.ui.search


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldebaran.domain.entities.remote.TvResponse

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentSuggestBinding
import com.aldebaran.domain.Result.Status.*
import com.themovie.helper.*
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.search.adapter.SuggestTvAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuggestTvFragment : BaseFragment<FragmentSuggestBinding>(), SuggestActivity.TvSearchFragmentListener {

    private val viewModel by viewModels<SuggestTvViewModel>()
    private val mAdapter by lazy { SuggestTvAdapter() }

    override fun getLayout(): Int {
        return R.layout.fragment_suggest
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
    }

    override fun onMain(savedInstanceState: Bundle?) {
        SuggestActivity.setTextListener(this)
        setupRecyclerView()
        observeSuggestData()
    }

    private fun setupRecyclerView(){
        mAdapter.setAdapterListener(object: OnAdapterListener<TvResponse> {
            override fun onClick(view: View, item: TvResponse) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id ?: 0)
                    putString("type", Constant.TV)
                }
                changeActivity<DetailActivity>(bundle)
                activity?.finish()
            }
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun observeSuggestData(){
        viewModel.getSuggestTv().observe(this, {
                when(it.status){
                    LOADING -> {setLog("Loading")}
                    SUCCESS -> {
                        if(binding.recyclerView.visibility == View.GONE)
                            binding.recyclerView.visible()
                        mAdapter.submitList(it.data?.results)
                    }
                    ERROR -> {
                        binding.recyclerView.gone()
                    }
                }
            })
    }

    override fun textChange(text: String) {
        viewModel.fetchSuggestTv(text)

    }
}
