package com.themovie.helper.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.themovie.R
import kotlinx.android.synthetic.main.no_internet.view.*

class NoInternetView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr), CompoundView {

    constructor(context: Context): this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    init {
        inflateView()
    }

    override fun inflateView() {
        LayoutInflater.from(context).inflate(R.layout.no_internet, this)
    }

    override fun initAttr(attrs: TypedArray?) {

    }

    fun retryOnClick(listener: OnClickListener){
        btn_retry.setOnClickListener(listener)
    }
}