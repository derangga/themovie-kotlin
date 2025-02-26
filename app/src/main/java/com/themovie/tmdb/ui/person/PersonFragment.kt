package com.themovie.tmdb.ui.person

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.themovie.core.page.BaseFragment
import com.themovie.core.event.EventObserver
import com.themovie.core.utils.changeActivity
import com.themovie.datasource.entities.ui.ArtistFilm

import com.themovie.tmdb.R
import com.themovie.tmdb.databinding.FragmentPersonBinding
import com.themovie.tmdb.helper.Constant
import com.themovie.tmdb.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment : BaseFragment<FragmentPersonBinding>() {

    private val artistFilmAdapter by lazy { PersonFilmAdapter(::onPersonFIlmItemClick) }
    private val artistPictAdapter by lazy { PersonImageAdapter() }
    private val viewModel by viewModels<PersonViewModel>()
    private var personId = 0

    override fun getLayout(): Int {
        return R.layout.fragment_person
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        arguments?.let {
            personId = PersonFragmentArgs.fromBundle(it).personId
        }

        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun onMain(savedInstanceState: Bundle?) {
        setupRecycler()
        subscribeUI()
        binding.header.searchIconVisibility(false)
        viewModel.getDetailPersonRequest(personId)
    }

    private fun setupRecycler(){
        binding.apply{
            castFilm.adapter = artistFilmAdapter
            castPhotos.adapter = artistPictAdapter
        }
    }

    private fun subscribeUI(){
        viewModel.eventError.observe(viewLifecycleOwner, EventObserver {
            if (it) networkErrorDialog.show(childFragmentManager, "")
        })
        
        viewModel.artist.observe(viewLifecycleOwner, { binding.cast = it })
        viewModel.artistPict.observe(viewLifecycleOwner, { artistPictAdapter.submitList(it) })
        viewModel.artistFilm.observe(viewLifecycleOwner, { artistFilmAdapter.submitList(it) })
    }
    
    private fun onPersonFIlmItemClick(movie: ArtistFilm) {
        val bundle = Bundle().apply {
            putInt("filmId", movie.id)
            putString("type", Constant.MOVIE)
        }
        changeActivity<DetailActivity>(bundle)
    }

    override fun delegateRetryEventDialog() {
        viewModel.getDetailPersonRequest(personId)
    }
}
