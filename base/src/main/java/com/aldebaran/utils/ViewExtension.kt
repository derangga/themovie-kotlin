package com.aldebaran.utils

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View?.navigateFragment(action: (View) -> Unit) {
    this?.run { action(this) }
}

fun RecyclerView.initLinearRecycler(
    context: Context,
    @RecyclerView.Orientation scrollOrientation: Int = RecyclerView.VERTICAL,
    reverse: Boolean = false,
) {
    layoutManager = LinearLayoutManager(context, scrollOrientation, reverse)
}