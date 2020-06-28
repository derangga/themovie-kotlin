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
import com.themovie.restapi.Result.Status.*
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
    private var personId = 0

    override fun getLayout(): Int {
        return R.layout.fragment_person
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity as DetailActivity).getDetailComponent().inject(this)
        arguments?.let {
            personId = PersonFragmentArgs.fromBundle(it).personId
        }

        binding.apply {
            lifecycleOwner = this@PersonFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        setupRecycler()
        subscribeUI()
        binding.header.setSearchVisibility(View.GONE)
        viewModel.getDetailPersonRequest(personId)
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
                changeActivity<DetailActivity>(bundle)
            }
        })
    }

    private fun subscribeUI(){
        viewModel.apply {
            detailPerson.observe(viewLifecycleOwner, Observer { res ->
                when(res.status){
                    SUCCESS -> {
                        hideLoading()
                        binding.cast = res?.data
                    }
                    ERROR -> {
                        showNetworkError(false){
                            viewModel.getDetailPersonRequest(personId)
                        }
                    }
                    LOADING -> { showLoading() }
                }
            })

            personFilm.observe(viewLifecycleOwner, Observer { res ->
                when(res.status){
                    SUCCESS -> {
                        personFilmAdapter.submitList(res.data?.filmographies)
                    }
                    else -> {}
                }
            })

            personPict.observe(viewLifecycleOwner, Observer { res ->
                when(res.status){
                    SUCCESS -> {
                        if(res.data?.imageList.isNullOrEmpty()){
                            binding.castImgEmpty.visible()
                            binding.castPhotos.gone()
                        }
                        personImageAdapter.setImageList(res?.data?.imageList)
                    }
                    else -> {}
                }
            })
        }
    }

    private fun showLoading(){
        binding.apply {
            shimmerPerson.visible()
            personLayout.invisible()
        }
    }

    private fun hideLoading(){
        binding.apply {
            shimmerPerson.gone()
            personLayout.visible()
        }
    }
}
