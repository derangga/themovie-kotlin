package com.themovie.ui.discover


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentTvBinding
import com.themovie.di.main.MainViewModelFactory
import com.themovie.helper.Constant
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.changeActivity
import com.themovie.model.db.Tv
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.TvAdapter
import com.themovie.ui.main.MainActivity
import com.themovie.ui.search.SuggestActivity
import kotlinx.android.synthetic.main.fragment_tv.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class TvFragment : BaseFragment<FragmentTvBinding>(), SwipeRefreshLayout.OnRefreshListener {

    @Inject lateinit var factory: MainViewModelFactory
    private lateinit var tvAdapter: TvAdapter
    private val viewModel by viewModels<TvViewModel> { factory }

    override fun getLayout(): Int {
        return R.layout.fragment_tv
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity as MainActivity).getMainComponent()?.inject(this)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@TvFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        swipe.setOnRefreshListener(this)
        binding.header.apply {
            setLogoVisibility(View.GONE)
            setTitleText(resources.getString(R.string.home_title_4))
            setBackButtonVisibility(View.VISIBLE)
            setBackButtonOnClickListener(View.OnClickListener {
                val action = TvFragmentDirections.actionTvFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            })
            setSearchButtonOnClickListener(View.OnClickListener { changeActivity<SuggestActivity>() })
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
        tvAdapter = TvAdapter()
        tv_rec.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tvAdapter
        }

        tvAdapter.setOnClickAdapter(object: OnAdapterListener<Tv>{
            override fun onClick(view: View, item: Tv) {
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
            getTvLiveData().observe(this@TvFragment,
                Observer<PagedList<Tv>>{
                    tvAdapter.submitList(it)
                    swipe.isRefreshing = false
                })

            getLoadState().observe(this@TvFragment,
                Observer {
                    tvAdapter.setLoadState(it)
                })
        }
    }
}
