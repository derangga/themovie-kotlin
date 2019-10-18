package com.themovie.ui.person

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovie.MyApplication
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.databinding.ActivityPersonBinding
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchPersonData
import com.themovie.model.online.person.Filmography
import com.themovie.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_person.*
import javax.inject.Inject

class PersonActivity : BaseActivity() {

    @Inject lateinit var viewmodelFactory: PersonViewModelFactory
    private lateinit var personFilmAdapter: PersonFilmAdapter
    private lateinit var personViewModel: PersonViewModel
    private lateinit var binding: ActivityPersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Biography"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (application as MyApplication).getAppComponent().inject(this)
        personViewModel = ViewModelProvider(this, viewmodelFactory).get(PersonViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_person)

        binding.let {
            it.vm = personViewModel
            it.lifecycleOwner = this
        }

        setupRecycler()
        getPersonData()
        getLoadStatus()
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    private fun setupRecycler(){
        personFilmAdapter = PersonFilmAdapter()

        cast_recycler.apply {
            layoutManager = LinearLayoutManager(this@PersonActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = personFilmAdapter
        }

        personFilmAdapter.setOnItemCLickListener(object: PersonFilmAdapter.OnClickItemListener{
            override fun onClick(view: View?, personFilm: Filmography) {
                val bundle = Bundle().apply {
                    putInt("id", personFilm.id)
                    putString("image", personFilm.backdrop_path.toString())
                    putString("detail", Constant.MOVIE)
                }

                changeActivity(bundle, DetailActivity::class.java)
            }
        })
    }

    private fun getPersonData(){
        personViewModel.getPersonData(getBundle()!!.getInt("person")).observe(
            this, Observer<FetchPersonData> {
                personViewModel.setPersonData(it.personResponse!!)
                personFilmAdapter.submitList(it.personFilmResponse?.filmographies)
            }
        )
    }

    private fun getLoadStatus(){
        personViewModel.getLoadStatus().observe(
            this, Observer<LoadDataState> {
                if(it == LoadDataState.LOADED) hideLoading()
                else {
                    showNetworkError()
                    binding.prRetry.setOnClickListener {
                        showLoading()
                        getPersonData()
                    }
                }
            }
        )
    }

    private fun showLoading(){
        binding.apply {
            shimmerPerson.visibility = View.VISIBLE
            personLayout.visibility = View.INVISIBLE
            prNoInternet.visibility = View.GONE
        }
    }

    private fun hideLoading(){
        binding.apply {
            shimmerPerson.visibility = View.GONE
            personLayout.visibility = View.VISIBLE
            prNoInternet.visibility = View.GONE
        }

    }

    private fun showNetworkError(){
        binding.apply {
            shimmerPerson.visibility = View.GONE
            personLayout.visibility = View.INVISIBLE
            prNoInternet.visibility = View.VISIBLE
        }

    }

}
