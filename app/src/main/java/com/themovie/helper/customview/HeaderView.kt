package com.themovie.helper.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.appbar.AppBarLayout
import com.themovie.R
import kotlinx.android.synthetic.main.header.view.*

class HeaderView(
    context: Context,
    attrs: AttributeSet? = null
): AppBarLayout(context, attrs), CompoundView {

    constructor(context: Context): this(context, null)

    init {
        inflateView()
    }

    override fun inflateView() {
        LayoutInflater.from(context).inflate(R.layout.header, this)
    }

    override fun initAttr(attrs: TypedArray?) {

    }

    fun setBackButtonVisibility(status: Int){
        h_back.visibility = status
    }

    fun setLogoVisibility(status: Int){
        h_logo.visibility = status
    }

    fun setSearchVisibility(status: Int){
        h_search.visibility = status
    }

    fun setTitleText(title: String){
        h_title.text = title
    }

    fun setBackButtonOnClickListener(listener: OnClickListener){
        h_back.setOnClickListener(listener)
    }

    fun setSearchButtonOnClickListener(listener: OnClickListener){
        h_search.setOnClickListener(listener)
    }
}