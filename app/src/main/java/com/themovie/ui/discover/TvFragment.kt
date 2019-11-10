package com.themovie.ui.discover


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.themovie.MyApplication

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentTvBinding
import com.themovie.helper.Constant
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Tv
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.TvAdapter
import kotlinx.android.synthetic.main.fragment_tv.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class TvFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    @Inject lateinit var viewModelFactory: TvViewModelFactory
    private lateinit var tvAdapter: TvAdapter
    private lateinit var viewModel: TvViewModel
    private lateinit var binding: FragmentTvBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv, container, false)
        (activity?.application as MyApplication).getAppComponent().inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TvViewModel::class.java)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@TvFragment
        }
        return binding.root
    }

    override fun onMain(savedInstanceState: Bundle?) {
        swipe.setOnRefreshListener(this)
        binding.header?.apply {
            setLogoVisibility(View.GONE)
            setTitleText(resources.getString(R.string.home_title_4))
            setBackButtonVisibility(View.VISIBLE)
            setBackButtonOnClickListener(View.OnClickListener {
                val action = TvFragmentDirections.actionTvFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            })
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
                    putInt("filmId", item.id)
                    putString("type", Constant.TV)
                }
                changeActivity(bundle, DetailActivity::class.java)
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
