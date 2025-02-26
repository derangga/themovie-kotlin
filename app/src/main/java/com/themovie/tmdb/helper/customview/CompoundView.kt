package com.themovie.tmdb.helper.customview

import android.content.res.TypedArray

interface CompoundView {
    fun inflateView()
    fun initAttr(attrs: TypedArray?)
}