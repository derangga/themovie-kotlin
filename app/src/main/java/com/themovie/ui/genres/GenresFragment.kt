package com.themovie.ui.genres


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentGenresBinding
import com.themovie.di.main.MainViewModelFactory
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Genre
import com.themovie.ui.main.MainActivity
import com.themovie.ui.main.adapter.GenreAdapter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class GenresFragment : BaseFragment<FragmentGenresBinding>() {

    @Inject lateinit var factory: MainViewModelFactory
    private lateinit var genreAdapter: GenreAdapter
    private val viewModel by viewModels<GenreViewModel> { factory }

    override fun getLayout(): Int {
        return R.layout.fragment_genres
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        (activity as MainActivity).getMainComponent()?.inject(this)
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
            setBackButtonOnClickListener(View.OnClickListener {
                val action = GenresFragmentDirections.actionGenresFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            })
        }
    }

    private fun setupRecycler(){
        genreAdapter = GenreAdapter()
        binding.genreList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = genreAdapter
        }

        genreAdapter.setGenreClickListener(object: OnAdapterListener<Genre>{
            override fun onClick(view: View, item: Genre) {
                val action = GenresFragmentDirections
                    .actionGenresFragmentToMovieWithGenreFragment(item.id ?: 0, item.name.orEmpty(), "genreList")
                Navigation.findNavController(view).navigate(action)
            }
        })
    }

    private fun getGenreList(){
        viewModel.genreMovies.observe(viewLifecycleOwner,
            Observer<List<Genre>>{
                genreAdapter.submitList(it)
            })
    }
}
