package com.themovie.helper

import android.view.View

interface OnAdapterListener<T> {
    fun onClick(view: View, item: T)
}