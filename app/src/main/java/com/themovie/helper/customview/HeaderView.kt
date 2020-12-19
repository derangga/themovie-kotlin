package com.themovie.helper.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.aldebaran.utils.visibleIf
import com.google.android.material.appbar.AppBarLayout
import com.themovie.R

class HeaderView(
    context: Context,
    attrs: AttributeSet? = null
): AppBarLayout(context, attrs), CompoundView {

    constructor(context: Context): this(context, null)

    private val backButton by lazy { findViewById<ImageButton>(R.id.h_back) }
    private val tmdbLogo by lazy { findViewById<ImageView>(R.id.h_logo) }
    private val searchButton by lazy { findViewById<ImageButton>(R.id.h_search) }
    private val titleText by lazy { findViewById<TextView>(R.id.h_title) }

    init {
        inflateView()
    }

    override fun inflateView() {
        LayoutInflater.from(context).inflate(R.layout.header, this)
    }

    override fun initAttr(attrs: TypedArray?) {

    }

    fun backButtonVisibility(isVisible: Boolean): HeaderView {
        backButton.visibleIf { isVisible }
        return this
    }

    fun logoVisibility(isVisible: Boolean): HeaderView {
        tmdbLogo.visibleIf { isVisible }
        return this
    }

    fun searchIconVisibility(isVisible: Boolean): HeaderView {
        searchButton.visibleIf { isVisible }
        return this
    }

    fun titleText(title: String): HeaderView {
        titleText.text = title
        return this
    }

    fun backButtonOnClickListener(listener: OnClickListener) : HeaderView{
        backButton.setOnClickListener(listener)
        return this
    }

    fun searchButtonOnClickListener(listener: OnClickListener) : HeaderView {
        searchButton.setOnClickListener(listener)
        return this
    }
}