package com.themovie.ui.person

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.base.BaseFragment
import com.aldebaran.domain.Result.Status.*
import com.aldebaran.domain.entities.remote.person.Filmography
import com.aldebaran.utils.*

import com.themovie.R
import com.themovie.databinding.FragmentPersonBinding
import com.themovie.helper.*
import com.themovie.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment : BaseFragment<FragmentPersonBinding>() {

    private val personFilmAdapter by lazy { PersonFilmAdapter(::onPersonFIlmItemClick) }
    private val personImageAdapter by lazy { PersonImageAdapter() }
    private val viewModel by viewModels<PersonViewModel>()
    private var personId = 0

    override fun getLayout(): Int {
        return R.layout.fragment_person
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
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
        binding.apply{
            castFilm.apply {
                initLinearRecycler(requireContext(), RecyclerView.HORIZONTAL)
                adapter = personFilmAdapter
            }

            castPhotos.apply {
                initLinearRecycler(requireContext(), RecyclerView.HORIZONTAL)
                adapter = personImageAdapter
            }
        }
    }

    private fun subscribeUI(){
        viewModel.apply {
            detailPerson.observe(viewLifecycleOwner, { res ->
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

            personFilm.observe(viewLifecycleOwner, { res ->
                when(res.status){
                    SUCCESS -> {
                        personFilmAdapter.submitList(res.data?.filmographies)
                    }
                    else -> {}
                }
            })

            personPict.observe(viewLifecycleOwner, { res ->
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

    private fun onPersonFIlmItemClick(movie: Filmography) {
        val bundle = Bundle().apply {
            putInt("filmId", movie.id ?: 0)
            putString("type", Constant.MOVIE)
        }
        changeActivity<DetailActivity>(bundle)
    }
}
