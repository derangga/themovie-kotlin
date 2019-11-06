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
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.themovie.MyApplication
import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentMoviesBinding
import com.themovie.helper.Constant
import com.themovie.model.db.Movies
import com.themovie.ui.detail.DetailActivity
import com.themovie.ui.discover.MovieViewModel
import com.themovie.ui.discover.MovieViewModelFactory
import com.themovie.ui.discover.adapter.MovieAdapter
import kotlinx.android.synthetic.main.header.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MovieWithGenreFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    @Inject lateinit var movieFactory: MovieViewModelFactory
    private lateinit var viewModel: MovieViewModel
    private lateinit var mAdapter: MovieAdapter
    private lateinit var binding: FragmentMoviesBinding
    private var destinationBackPress = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        (activity?.application as MyApplication).getAppComponent().inject(this)
        arguments?.let {
            MovieViewModel.genre = MovieWithGenreFragmentArgs.fromBundle(it).genreId.toString()
            destinationBackPress = MovieWithGenreFragmentArgs.fromBundle(it).from
        }
        viewModel = ViewModelProvider(this, movieFactory).get(MovieViewModel::class.java)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@MovieWithGenreFragment
        }

        return binding.root
    }

    override fun onMain(savedInstanceState: Bundle?) {
        binding.swipe.setOnRefreshListener(this)
        h_logo.visibility = View.GONE
        val action = if(destinationBackPress == "home"){
            MovieWithGenreFragmentDirections.actionMovieWithGenreFragmentToHomeFragment()
        } else MovieWithGenreFragmentDirections.actionMovieWithGenreFragmentToGenresFragment()

        h_back.apply {
            visibility = View.VISIBLE
            setOnClickListener { Navigation.findNavController(it).navigate(action) }
        }
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
        mAdapter = MovieAdapter()
        binding.movieRec.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mAdapter.setOnClickAdapter(object: MovieAdapter.OnClickAdapterListener{
            override fun onItemClick(view: View?, movies: Movies) {
                val bundle = Bundle().apply {
                    putInt("filmId", movies.id)
                    putString("type", Constant.MOVIE)
                }
                changeActivity(bundle, DetailActivity::class.java)
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
            getMovieLiveData().observe(this@MovieWithGenreFragment,
                Observer<PagedList<Movies>>{
                    mAdapter.submitList(it)
                    binding.swipe.isRefreshing = false
                })

            getLoadState().observe(this@MovieWithGenreFragment,
                Observer{
                    mAdapter.setLoadState(it)
                })
        }
    }
}
