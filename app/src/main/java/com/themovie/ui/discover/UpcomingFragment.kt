package com.themovie.ui.discover


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.themovie.databinding.FragmentUpcomingBinding
import com.themovie.model.online.discovermv.Movies
import com.themovie.ui.discover.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_upcoming.*
import kotlinx.android.synthetic.main.header.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class UpcomingFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    @Inject lateinit var upcomingViewFactory: UpcomingViewModelFactory
    private lateinit var viewModel: UpComingViewModel
    private lateinit var mAdapter: MovieAdapter
    private lateinit var binding: FragmentUpcomingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming, container, false)
        val view = binding.root
        (activity?.application as MyApplication).getAppComponent().inject(this)

        viewModel = ViewModelProvider(this, upcomingViewFactory).get(UpComingViewModel::class.java)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@UpcomingFragment
        }
        return view
    }

    override fun onMain(savedInstanceState: Bundle?) {
        swipe.setOnRefreshListener(this)
        h_logo.visibility = View.GONE
        h_back.visibility = View.VISIBLE
        h_back.setOnClickListener {
            val action = UpcomingFragmentDirections.actionUpcomingFragmentToHomeFragment()
            Navigation.findNavController(it).navigate(action)
        }
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
        mAdapter = MovieAdapter()
        upcoming_rec.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mAdapter.setOnClickAdapter(object: MovieAdapter.OnClickAdapterListener{
            override fun onItemClick(view: View?, movies: Movies, imageViewRes: ImageView) {

            }
        })

        mAdapter.setOnErrorClickListener(object: MovieAdapter.OnErrorClickListener{
            override fun onClick(view: View?) {
                viewModel.retry()
            }
        })
    }

    private fun getUpcomingMovie(){
        viewModel.apply {
            getUpcomingData().observe(this@UpcomingFragment,
                Observer<PagedList<Movies>> {
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
