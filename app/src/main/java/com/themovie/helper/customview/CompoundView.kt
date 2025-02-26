package com.themovie.helper.customview

import android.content.res.TypedArray

interface CompoundView {
    fun inflateView()
    fun initAttr(attrs: TypedArray?)
}