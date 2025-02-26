package com.themovie.tmdb.ui.genres


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.themovie.core.page.BaseFragment
import com.themovie.core.utils.navigateFragment
import com.themovie.datasource.entities.ui.Genre

import com.themovie.tmdb.R
import com.themovie.tmdb.databinding.FragmentGenresBinding
import com.themovie.tmdb.ui.main.adapter.GenreAdapter
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
        binding.header.logoVisibility(false)
            .backButtonVisibility(true)
            .searchIconVisibility(false)
            .titleText(resources.getString(R.string.home_title_3))
            .backButtonOnClickListener {
                activity?.onBackPressed()
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
        view.navigateFragment { Navigation.findNavController(it).navigate(action) }
    }
}
