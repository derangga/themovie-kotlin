package com.themovie.ui.search

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.themovie.R
import com.themovie.base.BaseActivity
import com.themovie.databinding.ActivitySearchBinding
import com.themovie.helper.ViewPagerFragment
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchActivity : BaseActivity() {

    private lateinit var imm: InputMethodManager
    private lateinit var binding: ActivitySearchBinding
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        initTab()
        binding.hSearch?.requestFocus()
        h_back.setOnClickListener {
            hideSoftKeyboard(findViewById(android.R.id.content))
            onBackPressed()
        }

        searchSoftKeyboardAction()
        textStream()
    }

    override fun onPause() {
        if(::job.isInitialized){
            job.cancel()
        }
        super.onPause()
    }

    private fun initTab(){
        binding.apply {
            tabLayout?.apply {
                addTab(this.newTab().setText("Movies"))
                addTab(this.newTab().setText("Tv"))
            }
            val pagerAdapter = ViewPagerFragment(supportFragmentManager)
            pagerAdapter.apply {
                addFragment("Movies", SuggestMovieFragment())
                addFragment("Tv", SuggestTvFragment())
            }
            viewPager?.apply {
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
                        tabLayout.visibility = View.GONE
                        viewPager.visibility = View.GONE
                    } else{
                        tabLayout.visibility = View.VISIBLE
                        viewPager.visibility = View.VISIBLE
                    }
                }

                if(searchText == searchFor) return

                searchFor = searchText

                job = CoroutineScope(Main).launch {
                    delay(500)
                    if(searchText != searchFor)
                        return@launch

                    movieListener?.textChange(s.toString())
                    tvListener?.textChange(s.toString())
                }
            }
        })
    }

    private fun searchSoftKeyboardAction(){
        binding.hSearch.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                Toast.makeText(this, h_search.text.toString(), Toast.LENGTH_SHORT).show()
            }
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
