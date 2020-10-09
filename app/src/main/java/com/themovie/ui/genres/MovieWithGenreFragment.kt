package com.themovie.ui.genres


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aldebaran.domain.entities.remote.MovieResponse
import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentMoviesBinding
import com.themovie.helper.Constant
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.changeActivity
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.MovieViewModel
import com.themovie.ui.discover.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieWithGenreFragment : BaseFragment<FragmentMoviesBinding>(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by viewModels<MovieViewModel>()
    private val mAdapter by lazy { MovieAdapter() }
    private var destinationBackPress = ""
    private var title = ""
    private var genreId = ""

    override fun getLayout(): Int {
        return R.layout.fragment_movies
    }

    override fun onCreateViewSetup(savedInstanceState: Bundle?) {
        arguments?.let {
            genreId = MovieWithGenreFragmentArgs.fromBundle(it).genreId.toString()
            destinationBackPress = MovieWithGenreFragmentArgs.fromBundle(it).from
            title = MovieWithGenreFragmentArgs.fromBundle(it).genreName
        }

        binding.apply {
            vm = viewModel
            lifecycleOwner = this@MovieWithGenreFragment
        }
    }

    override fun onMain(savedInstanceState: Bundle?) {
        val action = if(destinationBackPress == "home"){
            MovieWithGenreFragmentDirections.actionMovieWithGenreFragmentToHomeFragment()
        } else MovieWithGenreFragmentDirections.actionMovieWithGenreFragmentToGenresFragment()

        binding.header.apply {
            setLogoVisibility(View.GONE)
            setBackButtonVisibility(View.VISIBLE)
            setSearchVisibility(View.GONE)
            setTitleText("Genres: $title")
            setBackButtonOnClickListener {
                Navigation.findNavController(it).navigate(action)
            }
        }
        binding.swipe.setOnRefreshListener(this)
        viewModel.resetMovieWithGenre(genreId)

        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() { Navigation.findNavController(view!!).navigate(action) }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        recyclerViewSetup()
    }

    override fun onStart() {
        super.onStart()
        getDiscoverMovies()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSubscribing()
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    private fun recyclerViewSetup(){
        binding.movieRec.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mAdapter.setOnClickAdapter(object: OnAdapterListener<MovieResponse>{
            override fun onClick(view: View, item: MovieResponse) {
                val bundle = Bundle().apply {
                    putInt("filmId", item.id ?: 0)
                    putString("type", Constant.MOVIE)
                }
                changeActivity<DetailActivity>(bundle)
            }
        })

        mAdapter.setOnErrorClickListener(object: MovieAdapter.OnErrorClickListener{
            override fun onClick(view: View?) {
                viewModel.retry()
            }
        })
    }

    private fun getDiscoverMovies(){
        viewModel.apply {
            getMovieLiveData().observe(this@MovieWithGenreFragment, {
                    mAdapter.submitList(it)
                    binding.swipe.isRefreshing = false
            })

            getLoadState().observe(this@MovieWithGenreFragment, {
                    mAdapter.setLoadState(it)
            })
        }
    }
}
