package com.themovie.ui.person

import android.os.Bundle
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

class PersonActivity : AppCompatActivity() {

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

    private fun setupRecycler(){
        personfilmAdapter = PersonFilmAdapter()

        cast_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        cast_recycler.adapter = personfilmAdapter
    }

    private fun getPersonData(){
        personViewModel.getPersonData(ApiUrl.TOKEN, intent.extras!!.getInt("person")).observe(
            this, Observer<FetchPersonData> {
                personViewModel.setPersonData(it.personResponse)
                personfilmAdapter.submitList(it.personFilmResponse.filmographies)
            }
        )
    }

    private fun getLoadStatus(){
        personViewModel.getLoadStatus().observe(
            this, Observer<LoadDataState> {

            }
        )
    }

}
