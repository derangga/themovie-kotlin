package com.themovie.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.aldebaran.core.BaseActivity
import com.aldebaran.utils.TextChangeWatcher
import com.aldebaran.utils.changeActivity
import com.aldebaran.utils.gone
import com.aldebaran.utils.visible
import com.themovie.R
import com.themovie.databinding.ActivitySearchBinding
import com.themovie.helper.ViewPagerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SuggestActivity : BaseActivity<ActivitySearchBinding>() {

    private lateinit var imm: InputMethodManager

    private val viewModel by viewModels<SuggestViewModel>()

    override fun getLayout(): Int {
        return R.layout.activity_search
    }

    private val searchTextWatcher by lazy {
        TextChangeWatcher(afterTextChanged =  this::afterTextChanged)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        setupView()
    }

    private fun setupView(){
        binding.apply {
            tabLayout.apply {
                addTab(this.newTab().setText("Movies"))
                addTab(this.newTab().setText("Tv"))
            }
            val pagerAdapter = ViewPagerFragment(supportFragmentManager)
            pagerAdapter.apply {
                addFragment("Movies", SuggestMovieFragment())
                addFragment("Tv", SuggestTvFragment())
            }
            viewPager.apply {
                adapter = pagerAdapter
                offscreenPageLimit = 2
            }
            tabLayout.setupWithViewPager(viewPager)
            pagerAdapter.notifyDataSetChanged()
        }
        binding.hSearch.addTextChangedListener(searchTextWatcher)

        binding.hSearch.requestFocus()

        binding.hBack.setOnClickListener { onBackPressed() }

        searchSoftKeyboardAction()
    }

    private fun showSoftKeyboard(view: View?){
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideSoftKeyboard(view: View?){
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun afterTextChanged(text: Editable?) {
        val searchText = text.toString().trim()

        binding.apply {
            if(binding.hSearch.text.toString().isEmpty()){
                tabLayout.gone()
                viewPager.gone()
            } else{
                tabLayout.visible()
                viewPager.visible()
            }
        }

        if(searchText == searchTextWatcher.searchFor) return

        searchTextWatcher.searchFor = searchText

        lifecycleScope.launchWhenStarted {
            delay(500)
            if(searchText != searchTextWatcher.searchFor)
                return@launchWhenStarted

            viewModel.searchMovieAndTv(searchText)
        }
    }

    private fun searchSoftKeyboardAction(){
        binding.hSearch.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH && binding.hSearch.text.isNotEmpty()){
                val bundle = Bundle().apply { putString("query", binding.hSearch.text.toString()) }
                changeActivity<SearchActivity>(bundle)
                finish()
            } else showToastMessage(resources.getString(R.string.suggest_search_1))
            true
        }
    }
}
