package com.themovie.ui.genres


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.aldebaran.base.BaseFragment
import com.aldebaran.domain.entities.local.GenreEntity
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
        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val action = GenresFragmentDirections.actionGenresFragmentToHomeFragment()
                Navigation.findNavController(view!!).navigate(action)
            }}
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        binding.header.apply {
            setLogoVisibility(View.GONE)
            setTitleText(resources.getString(R.string.home_title_3))
            setBackButtonVisibility(View.VISIBLE)
            setSearchVisibility(View.GONE)
            setBackButtonOnClickListener {
                val action = GenresFragmentDirections.actionGenresFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    private fun setupRecycler(){
        binding.genreList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = genreAdapter
        }
    }

    private fun getGenreList(){
        viewModel.genreMovies.observe(viewLifecycleOwner, { genreAdapter.submitList(it) })
    }

    private fun onGenreItemClick(genre: GenreEntity) {
        val action = GenresFragmentDirections
            .actionGenresFragmentToMovieWithGenreFragment(genre.id ?: 0, genre.name.orEmpty(), "genreList")
        view?.navigateFragment { Navigation.findNavController(it).navigate(action) }
    }
}
