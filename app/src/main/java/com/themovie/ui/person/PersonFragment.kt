package com.themovie.ui.person


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
import com.themovie.databinding.FragmentPersonBinding
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.helper.OnAdapterListener
import com.themovie.model.online.FetchPersonData
import com.themovie.model.online.person.Filmography
import com.themovie.ui.detail.DetailActivity
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class PersonFragment : BaseFragment() {

    @Inject lateinit var viewModelFactory: PersonViewModelFactory
    private lateinit var personFilmAdapter: PersonFilmAdapter
    private lateinit var personViewModel: PersonViewModel
    private lateinit var binding: FragmentPersonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_person, container, false)
        (activity?.application as MyApplication).getAppComponent().inject(this)
        arguments?.let {
            val personId = PersonFragmentArgs.fromBundle(it).personId
            PersonViewModel.setPersonId(personId)
        }
        personViewModel = ViewModelProvider(this, viewModelFactory).get(PersonViewModel::class.java)
        binding.apply {
            vm = personViewModel
            lifecycleOwner = this@PersonFragment
        }
        return binding.root
    }

    override fun onMain(savedInstanceState: Bundle?) {
        setupRecycler()
        getPersonData()
        getLoadStatus()
        binding.header.setSearchVisibility(View.GONE)
    }

    private fun setupRecycler(){
        personFilmAdapter = PersonFilmAdapter()

        binding.castRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = personFilmAdapter
        }

        personFilmAdapter.setOnItemCLickListener(object: OnAdapterListener<Filmography>{
            override fun onClick(view: View, item: Filmography) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id)
                    putString("type", Constant.MOVIE)
                }
                changeActivity(bundle, DetailActivity::class.java)
            }
        })
    }

    private fun getPersonData(){
        personViewModel.getPersonData().observe(
            this, Observer<FetchPersonData> {
                personViewModel.setPersonData(it.personResponse!!)
                personFilmAdapter.submitList(it.personFilmResponse?.filmographies)
            }
        )
    }

    private fun getLoadStatus(){
        personViewModel.getLoadStatus().observe(
            this, Observer<LoadDataState> {
                if(it == LoadDataState.LOADED) hideLoading()
                else {
                    showNetworkError()
                    binding.prRetry.setOnClickListener {
                        showLoading()
                        getPersonData()
                    }
                }
            }
        )
    }

    private fun showLoading(){
        binding.apply {
            shimmerPerson.visibility = View.VISIBLE
            personLayout.visibility = View.INVISIBLE
            prNoInternet.visibility = View.GONE
        }
    }

    private fun hideLoading(){
        binding.apply {
            shimmerPerson.visibility = View.GONE
            personLayout.visibility = View.VISIBLE
            prNoInternet.visibility = View.GONE
        }

    }

    private fun showNetworkError(){
        binding.apply {
            shimmerPerson.visibility = View.GONE
            personLayout.visibility = View.INVISIBLE
            prNoInternet.visibility = View.VISIBLE
        }

    }
}
