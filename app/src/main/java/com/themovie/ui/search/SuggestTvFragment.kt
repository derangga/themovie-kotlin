package com.themovie.ui.search


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentSuggestBinding
import com.themovie.di.suggest.SuggestViewModelFactory
import com.themovie.helper.*
import com.themovie.model.db.Tv
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.restapi.Result
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.search.adapter.SuggestTvAdapter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SuggestTvFragment : BaseFragment<FragmentSuggestBinding>(), SuggestActivity.TvSearchFragmentListener {

    @Inject lateinit var factory: SuggestViewModelFactory
    private val viewModel by viewModels<SuggestTvViewModel> { factory }
    private lateinit var mAdapter: SuggestTvAdapter

    override fun getLayout(): Int {
        return R.layout.fragment_suggest
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity as SuggestActivity).getComponent()?.inject(this)
        binding.lifecycleOwner = this
    }

    override fun onMain(savedInstanceState: Bundle?) {
        SuggestActivity.setTextListener(this)
        setupRecyclerView()
        observeSuggestData()
    }

    private fun setupRecyclerView(){
        mAdapter = SuggestTvAdapter()
        mAdapter.setAdapterListener(object: OnAdapterListener<Tv> {
            override fun onClick(view: View, item: Tv) {
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
        viewModel.getSuggestTv().observe(this,
            Observer<Result<TvResponse>> {
                when(it.status){
                    Result.Status.LOADING -> {setLog("Loading")}
                    Result.Status.SUCCESS -> {
                        if(binding.recyclerView.visibility == View.GONE)
                            binding.recyclerView.visible()
                        mAdapter.submitList(it.data?.results)
                    }
                    Result.Status.ERROR -> {
                        binding.recyclerView.gone()
                    }
                }
            })
    }

    override fun textChange(text: String) {
        viewModel.fetchSuggestTv(text)

    }
}
