package com.themovie.ui.genres


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.aldebaran.core.BaseFragment
import com.aldebaran.domain.entities.ui.Genre
import com.aldebaran.utils.navigateFragment

import com.themovie.R
import com.themovie.databinding.FragmentGenresBinding
import com.themovie.ui.main.adapter.GenreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenresFragment : BaseFragment<FragmentGenresBinding>() {

    private val genreAdapter by lazy { GenreAdapter(::onGenreItemClick) }
    private val viewModel by viewModels<GenreViewModel>()

    override fun getLayout(): Int {
        return R.layout.fragment_genres
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@GenresFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        setupRecycler()
        getGenreList()
        binding.header.apply {
            setLogoVisibility(View.GONE)
            setTitleText(resources.getString(R.string.home_title_3))
            setBackButtonVisibility(View.VISIBLE)
            setSearchVisibility(View.GONE)
            setBackButtonOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun setupRecycler(){
        binding.genreList.adapter = genreAdapter
    }

    private fun getGenreList(){
        viewModel.genreMovies.observe(viewLifecycleOwner, { genreAdapter.submitList(it) })
    }

    private fun onGenreItemClick(genre: Genre) {
        val action = GenresFragmentDirections
            .actionGenresFragmentToMovieWithGenreFragment(genre.id, genre.name, "genreList")
        view?.navigateFragment { Navigation.findNavController(it).navigate(action) }
    }
}
