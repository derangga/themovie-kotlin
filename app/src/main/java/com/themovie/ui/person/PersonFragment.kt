package com.themovie.ui.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentPersonBinding
import com.themovie.di.detail.DetailViewModelFactory
import com.themovie.helper.*
import com.themovie.model.online.person.Filmography
import com.themovie.ui.detail.DetailActivity
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class PersonFragment : BaseFragment<FragmentPersonBinding>() {

    @Inject lateinit var factory: DetailViewModelFactory
    private lateinit var personFilmAdapter: PersonFilmAdapter
    private lateinit var personImageAdapter: PersonImageAdapter
    private val viewModel by viewModels<PersonViewModel> { factory }

    override fun getLayout(): Int {
        return R.layout.fragment_person
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity as DetailActivity).getDetailComponent().inject(this)
        arguments?.let {
            val personId = PersonFragmentArgs.fromBundle(it).personId
            PersonViewModel.setPersonId(personId)
        }

        binding.apply {
            lifecycleOwner = this@PersonFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        setupRecycler()
        getPersonData()
        getLoadStatus()
        binding.header.setSearchVisibility(View.GONE)
    }

    private fun setupRecycler(){
        personFilmAdapter = PersonFilmAdapter()
        personImageAdapter = PersonImageAdapter()

        binding.apply{
            castFilm.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = personFilmAdapter
            }

            castPhotos.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = personImageAdapter
            }
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
        viewModel.getPersonData()
        viewModel.setPersonData().observe(
            viewLifecycleOwner, Observer {
                binding.cast = it.personResponse
                personFilmAdapter.submitList(it.personFilmResponse?.filmographies)
                personImageAdapter.setImageList(it.personImageResponse?.imageList)
                if(it.personImageResponse?.imageList?.size == 0){
                    binding.castImgEmpty.visibility = View.VISIBLE
                    binding.castPhotos.visibility = View.GONE
                }

            }
        )
    }

    private fun getLoadStatus(){
        viewModel.getLoadStatus().observe(
            viewLifecycleOwner, Observer {
                when (it) {
                    LoadDataState.LOADING -> showLoading()
                    LoadDataState.LOADED -> hideLoading()
                    else -> {
                        showNetworkError()
                        binding.prNoInternet.retryOnClick(View.OnClickListener {
                            showLoading()
                            viewModel.getPersonData()
                        })
                    }
                }
            }
        )
    }

    private fun showLoading(){
        binding.apply {
            shimmerPerson.visible()
            personLayout.invisible()
            prNoInternet.gone()
        }
    }

    private fun hideLoading(){
        binding.apply {
            shimmerPerson.gone()
            personLayout.visible()
            prNoInternet.gone()
        }

    }

    private fun showNetworkError(){
        binding.apply {
            shimmerPerson.gone()
            personLayout.invisible()
            prNoInternet.visible()
        }

    }
}
