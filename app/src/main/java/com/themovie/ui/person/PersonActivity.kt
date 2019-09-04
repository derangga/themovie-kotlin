package com.themovie.ui.person

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.databinding.ActivityPersonBinding
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchPersonData
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.activity_person.*

class PersonActivity : BaseActivity() {

    private lateinit var personfilmAdapter: PersonFilmAdapter
    private lateinit var personViewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Biography"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel::class.java)
        val binding: ActivityPersonBinding = DataBindingUtil.setContentView(this, R.layout.activity_person)
        binding.vm = personViewModel
        binding.lifecycleOwner = this
        setupRecycler()
        getPersonData()
        getLoadStatus()
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    private fun setupRecycler(){
        personfilmAdapter = PersonFilmAdapter()

        cast_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        cast_recycler.adapter = personfilmAdapter
    }

    private fun getPersonData(){
        personViewModel.getPersonData(ApiUrl.TOKEN, getBundle()!!.getInt("person")).observe(
            this, Observer<FetchPersonData> {
                personViewModel.setPersonData(it.personResponse)
                personfilmAdapter.submitList(it.personFilmResponse.filmographies)
            }
        )
    }

    private fun getLoadStatus(){
        personViewModel.getLoadStatus().observe(
            this, Observer<LoadDataState> {
                if(it == LoadDataState.LOADED) hideLoading()
                else {
                    showNetworkError()
                    pr_retry.setOnClickListener {
                        showLoading()
                        getPersonData()
                    }
                }
            }
        )
    }

    private fun showLoading(){
        shimmer_person.visibility = View.VISIBLE
        person_layout.visibility = View.INVISIBLE
        pr_no_internet.visibility = View.GONE
    }

    private fun hideLoading(){
        shimmer_person.visibility = View.GONE
        person_layout.visibility = View.VISIBLE
        pr_no_internet.visibility = View.GONE
    }

    private fun showNetworkError(){
        shimmer_person.visibility = View.GONE
        person_layout.visibility = View.INVISIBLE
        pr_no_internet.visibility = View.VISIBLE
    }

}
