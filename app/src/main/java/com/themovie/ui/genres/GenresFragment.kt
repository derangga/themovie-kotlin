package com.themovie.ui.genres


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovie.MyApplication

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentGenresBinding
import com.themovie.model.local.GenreLocal
import com.themovie.ui.main.adapter.GenreAdapter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class GenresFragment : BaseFragment() {

    @Inject lateinit var genreModelFactory: GenreViewModelFactory
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var viewModel: GenreViewModel
    private lateinit var binding: FragmentGenresBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_genres, container, false)
        (activity?.application as MyApplication).getAppComponent().inject(this)
        viewModel = ViewModelProvider(this, genreModelFactory).get(GenreViewModel::class.java)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@GenresFragment
        }

        return binding.root
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
    }

    private fun setupRecycler(){
        genreAdapter = GenreAdapter()
        binding.genreList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = genreAdapter
        }
    }

    private fun getGenreList(){
        viewModel.getGenreList().observe(this,
            Observer<List<GenreLocal>>{
                genreAdapter.submitList(it)
            })
    }
}
