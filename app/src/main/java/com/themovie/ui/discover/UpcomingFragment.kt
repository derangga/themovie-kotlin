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
import com.themovie.databinding.FragmentUpcomingBinding
import com.themovie.di.main.MainViewModelFactory
import com.themovie.helper.Constant
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.changeActivity
import com.themovie.model.db.Upcoming
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.adapter.UpcomingAdapter
import com.themovie.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_upcoming.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class UpcomingFragment : BaseFragment<FragmentUpcomingBinding>(), SwipeRefreshLayout.OnRefreshListener {

    @Inject lateinit var factory: MainViewModelFactory
    private val viewModel by viewModels<UpComingViewModel> { factory }
    private lateinit var mAdapter: UpcomingAdapter

    override fun getLayout(): Int {
        return R.layout.fragment_upcoming
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity as MainActivity).getMainComponent()?.inject(this)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@UpcomingFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        swipe.setOnRefreshListener(this)
        binding.header.apply {
            setLogoVisibility(View.GONE)
            setSearchVisibility(View.GONE)
            setBackButtonVisibility(View.VISIBLE)
            setTitleText(resources.getString(R.string.home_title_2))
            setBackButtonOnClickListener(View.OnClickListener {
                val action = UpcomingFragmentDirections.actionUpcomingFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            })
        }

        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val action = UpcomingFragmentDirections.actionUpcomingFragmentToHomeFragment()
                Navigation.findNavController(view!!).navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        recyclerViewSetup()
    }

    override fun onStart() {
        super.onStart()
        getUpcomingMovie()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSubscribing()
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    private fun recyclerViewSetup(){
        mAdapter = UpcomingAdapter()
        upcoming_rec.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mAdapter.setOnClickAdapter(object: OnAdapterListener<Upcoming>{
            override fun onClick(view: View, item: Upcoming) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id ?: 0)
                    putString("type", Constant.MOVIE)
                }
                changeActivity<DetailActivity>(bundle)
            }
        })

        mAdapter.setOnErrorClickListener(object: UpcomingAdapter.OnErrorClickListener{
            override fun onClick(view: View?) {
                viewModel.retry()
            }
        })
    }

    private fun getUpcomingMovie(){
        viewModel.apply {
            getUpcomingData().observe(this@UpcomingFragment,
                Observer<PagedList<Upcoming>> {
                    mAdapter.submitList(it)
                    swipe.isRefreshing = false
                })

            getLoadState().observe(this@UpcomingFragment,
                Observer{
                    mAdapter.setLoadState(it)
                })
        }
    }
}
