package com.aldebaran.utils

import android.view.View
import androidx.annotation.DimenRes

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

inline fun View?.navigateFragment(action: (View) -> Unit) {
    this?.run { action(this) }
}

fun View.getDimensionRes(@DimenRes dimens: Int): Int {
    return resources.getDimension(dimens).toInt()
}

inline fun View.visibleIf(invisible: Boolean = false, block: () -> Boolean) {
    this.visibility = if (block()) View.VISIBLE else (if (invisible) View.INVISIBLE else View.GONE)
}