package com.themovie.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.databinding.ActivitySearchBinding
import com.themovie.helper.ViewPagerFragment
import com.themovie.helper.changeActivity
import com.themovie.helper.gone
import com.themovie.helper.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SuggestActivity : BaseActivity<ActivitySearchBinding>() {

    private lateinit var imm: InputMethodManager

    override fun getLayout(): Int {
        return R.layout.activity_search
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        initTab()
        binding.hSearch.requestFocus()
        h_back.setOnClickListener {
            hideSoftKeyboard(findViewById(android.R.id.content))
            onBackPressed()
        }

        searchSoftKeyboardAction()
        textStream()
    }

    private fun initTab(){
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

    }

    private fun showSoftKeyboard(view: View?){
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideSoftKeyboard(view: View?){
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun textStream(){
        binding.hSearch.addTextChangedListener(object: TextWatcher {
            private var searchFor = ""

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()

                binding.apply {
                    if(hSearch.text.toString().isEmpty()){
                        tabLayout.gone()
                        viewPager.gone()
                    } else{
                        tabLayout.visible()
                        viewPager.visible()
                    }
                }

                if(searchText == searchFor) return

                searchFor = searchText

                lifecycleScope.launchWhenStarted {
                    delay(500)
                    if(searchText != searchFor)
                        return@launchWhenStarted

                    movieListener?.textChange(s.toString())
                    tvListener?.textChange(s.toString())
                }
            }
        })
    }

    private fun searchSoftKeyboardAction(){
        binding.hSearch.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH && binding.hSearch.text.isNotEmpty()){
                val bundle = Bundle().apply { putString("query", h_search.text.toString()) }
                changeActivity<SearchActivity>(bundle)
                finish()
            } else showToastMessage(resources.getString(R.string.suggest_search_1))
            false
        }
    }

    companion object{
        private var movieListener: MoviesSearchFragmentListener? = null
        private var tvListener: TvSearchFragmentListener? = null
        fun setTextListener(listener: MoviesSearchFragmentListener?){
            movieListener = listener
        }
        fun setTextListener(listener: TvSearchFragmentListener?){
            tvListener = listener
        }
    }

    interface MoviesSearchFragmentListener {
        fun textChange(text: String)
    }

    interface TvSearchFragmentListener {
        fun textChange(text: String)
    }
}
