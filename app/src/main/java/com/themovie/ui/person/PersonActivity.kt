package com.themovie.ui.person

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovie.MyApplication
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.databinding.ActivityPersonBinding
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchPersonData
import com.themovie.model.online.person.Filmography
import com.themovie.restapi.ApiUrl
import com.themovie.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_person.*
import javax.inject.Inject

class PersonActivity : BaseActivity() {

    @Inject lateinit var viewmodelFactory: PersonViewModelFactory
    private lateinit var personFilmAdapter: PersonFilmAdapter
    private lateinit var personViewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Biography"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (application as MyApplication).getAppComponent().inject(this)
        personViewModel = ViewModelProviders.of(this, viewmodelFactory).get(PersonViewModel::class.java)
        val binding: ActivityPersonBinding = DataBindingUtil.setContentView(this, R.layout.activity_person)

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
        personViewModel.getPersonData(ApiUrl.TOKEN, getBundle()!!.getInt("person")).observe(
            this, Observer<FetchPersonData> {
                personViewModel.setPersonData(it.personResponse)
                personFilmAdapter.submitList(it.personFilmResponse.filmographies)
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
