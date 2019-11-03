package com.themovie.helper.portraintview

import android.content.res.TypedArray

interface CompoundView {
    fun inflateView()
    fun initAttr(attrs: TypedArray?)
}