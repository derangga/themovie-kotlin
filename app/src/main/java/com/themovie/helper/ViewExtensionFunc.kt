package com.themovie.helper

import android.content.Intent
import android.provider.Settings
import android.view.View
import androidx.fragment.app.Fragment
import com.themovie.ui.bottomsheet.NoInternetBottomSheet

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun Fragment.showNetworkError(hideRetry: Boolean, action: () -> Unit){
    val bottom = NoInternetBottomSheet(hideRetry)
    bottom.setListener(object: NoInternetBottomSheet.ButtonListener{
        override fun onRetry() {
            action()
        }

        override fun onSetting() {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }
    })
    bottom.show(childFragmentManager, "")
}