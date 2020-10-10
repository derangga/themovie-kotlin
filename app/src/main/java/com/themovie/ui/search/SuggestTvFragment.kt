package com.themovie.ui.search


import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.aldebaran.base.BaseFragment
import com.aldebaran.domain.entities.remote.TvResponse

import com.themovie.R
import com.themovie.databinding.FragmentSuggestBinding
import com.aldebaran.domain.Result.Status.*
import com.aldebaran.utils.changeActivity
import com.aldebaran.utils.gone
import com.aldebaran.utils.initLinearRecycler
import com.aldebaran.utils.visible
import com.themovie.helper.*
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.search.adapter.SuggestTvAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuggestTvFragment : BaseFragment<FragmentSuggestBinding>(), SuggestActivity.TvSearchFragmentListener {

    private val viewModel by viewModels<SuggestTvViewModel>()
    private val mAdapter by lazy { SuggestTvAdapter(::onTvShowItemClick) }

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
        binding.recyclerView.apply {
            initLinearRecycler(requireContext())
            adapter = mAdapter
        }
    }

    private fun observeSuggestData(){
        viewModel.getSuggestTv().observe(this, {
                when(it.status){
                    LOADING -> {setLog("Loading")}
                    SUCCESS -> {
                        if(!binding.recyclerView.isVisible)
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

    private fun onTvShowItemClick(tv: TvResponse) {
        val bundle = Bundle().apply {
            putInt("filmId", tv.id ?: 0)
            putString("type", Constant.TV)
        }
        changeActivity<DetailActivity>(bundle)
        activity?.finish()
    }
}
