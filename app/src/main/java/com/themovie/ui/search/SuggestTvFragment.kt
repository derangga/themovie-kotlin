package com.themovie.ui.search


import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.aldebaran.core.BaseFragment

import com.themovie.R
import com.themovie.databinding.FragmentSuggestBinding
import com.aldebaran.domain.entities.ui.Tv
import com.aldebaran.utils.changeActivity
import com.themovie.helper.*
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.search.adapter.SuggestTvAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuggestTvFragment : BaseFragment<FragmentSuggestBinding>(), SuggestActivity.TvSearchFragmentListener {

    private val viewModel by activityViewModels<SuggestViewModel>()
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
        binding.recyclerView.adapter = mAdapter

    }

    private fun observeSuggestData(){
        viewModel.tvSearch.observe(viewLifecycleOwner, {
            mAdapter.submitList(it)
        })
    }

    override fun textChange(text: String) {
        viewModel.fetchSuggestTv(text)
    }

    private fun onTvShowItemClick(tv: Tv) {
        val bundle = Bundle().apply {
            putInt("filmId", tv.id)
            putString("type", Constant.TV)
        }
        changeActivity<DetailActivity>(bundle)
        activity?.finish()
    }
}
