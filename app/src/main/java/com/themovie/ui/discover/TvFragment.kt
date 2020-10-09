package com.themovie.ui.discover


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aldebaran.domain.entities.remote.TvResponse

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentTvBinding
import com.themovie.helper.Constant
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.changeActivity
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.TvAdapter
import com.themovie.ui.search.SuggestActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvFragment : BaseFragment<FragmentTvBinding>(), SwipeRefreshLayout.OnRefreshListener {

    private val tvAdapter by lazy { TvAdapter() }
    private val viewModel by viewModels<TvViewModel>()

    override fun getLayout(): Int {
        return R.layout.fragment_tv
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@TvFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        binding.swipe.setOnRefreshListener(this)
        binding.header.apply {
            setLogoVisibility(View.GONE)
            setTitleText(resources.getString(R.string.home_title_4))
            setBackButtonVisibility(View.VISIBLE)
            setBackButtonOnClickListener {
                val action = TvFragmentDirections.actionTvFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            }
            setSearchButtonOnClickListener { changeActivity<SuggestActivity>() }
        }


        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val action = TvFragmentDirections.actionTvFragmentToHomeFragment()
                Navigation.findNavController(view!!).navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        recyclerViewSetup()
    }

    override fun onStart() {
        super.onStart()
        getDiscoverTv()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSubscribing()
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    private fun recyclerViewSetup(){
        binding.tvRec.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tvAdapter
        }

        tvAdapter.setOnClickAdapter(object: OnAdapterListener<TvResponse>{
            override fun onClick(view: View, item: TvResponse) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id ?: 0)
                    putString("type", Constant.TV)
                }
                changeActivity<DetailActivity>(bundle)
            }
        })

        tvAdapter.setOnErrorClickListener(object: TvAdapter.OnErrorClickListener{
            override fun onClick(view: View?) {
                viewModel.retry()
            }
        })
    }

    private fun getDiscoverTv(){
        viewModel.apply {
            getTvLiveData().observe(this@TvFragment, {
                    tvAdapter.submitList(it)
                    binding.swipe.isRefreshing = false
                })

            getLoadState().observe(this@TvFragment, {
                    tvAdapter.setLoadState(it)
                })
        }
    }
}
