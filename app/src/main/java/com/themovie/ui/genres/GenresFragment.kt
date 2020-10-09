package com.themovie.ui.genres


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.aldebaran.domain.entities.local.GenreEntity

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentGenresBinding
import com.themovie.helper.OnAdapterListener
import com.themovie.ui.main.adapter.GenreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenresFragment : BaseFragment<FragmentGenresBinding>() {

    private val genreAdapter by lazy { GenreAdapter() }
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

        genreAdapter.setGenreClickListener(object: OnAdapterListener<GenreEntity>{
            override fun onClick(view: View, item: GenreEntity) {
                val action = GenresFragmentDirections
                    .actionGenresFragmentToMovieWithGenreFragment(item.id ?: 0, item.name.orEmpty(), "genreList")
                Navigation.findNavController(view).navigate(action)
            }
        })
    }

    private fun getGenreList(){
        viewModel.genreMovies.observe(viewLifecycleOwner, {
                genreAdapter.submitList(it)
            })
    }
}
