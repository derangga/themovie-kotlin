package com.themovie.tmdb.helper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerFragment(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList: ArrayList<Fragment> = ArrayList()
    private val fragmentTitleList: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

    fun addFragment(title: String, fragment: Fragment){
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }
}