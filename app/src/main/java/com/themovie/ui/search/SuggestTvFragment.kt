package com.themovie.ui.search


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
import com.themovie.databinding.FragmentSuggestBinding
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Tv
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.search.adapter.SuggestTvAdapter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SuggestTvFragment : BaseFragment(), SuggestActivity.TvSearchFragmentListener {

    @Inject lateinit var viewModelFactory: SuggestTvFactory
    private lateinit var binding: FragmentSuggestBinding
    private lateinit var viewModel: SuggestTvViewModel
    private lateinit var mAdapter: SuggestTvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_suggest, container, false)
        (activity?.application as MyApplication).getAppComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SuggestTvViewModel::class.java)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onMain(savedInstanceState: Bundle?) {
        SuggestActivity.setTextListener(this)
        setupRecyclerView()
        getLoadStatus()
    }

    private fun setupRecyclerView(){
        mAdapter = SuggestTvAdapter()
        mAdapter.setAdapterListener(object: OnAdapterListener<Tv> {
            override fun onClick(view: View, item: Tv) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id)
                    putString("type", Constant.TV)
                }
                changeActivity(bundle, DetailActivity::class.java)
                activity?.finish()
            }
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun getLoadStatus(){
        viewModel.getLoadStatus().observe(this, Observer {
            if(it == LoadDataState.LOADED) binding.recyclerView.visibility = View.VISIBLE
            else binding.recyclerView.visibility = View.GONE
        })
    }

    override fun textChange(text: String) {
        viewModel.getSuggestTv(text).observe(this,
            Observer{
                mAdapter.submitList(it)
            })
    }
}
