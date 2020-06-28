package com.themovie.helper

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

inline fun <reified T: AppCompatActivity> AppCompatActivity.changeActivity(){
    Intent(this, T::class.java)
        .also { startActivity(it) }
}

inline fun <reified T: AppCompatActivity> AppCompatActivity.changeActivity(bundle: Bundle){
    Intent(this, T::class.java)
        .also { intent ->
            intent.putExtras(bundle)
            startActivity(intent)
        }
}

inline fun <reified T: AppCompatActivity> Fragment.changeActivity(){
    Intent(context, T::class.java)
        .also { startActivity(it) }
}

inline fun <reified T: AppCompatActivity> Fragment.changeActivity(bundle: Bundle){
    Intent(context, T::class.java)
        .also { intent ->
            intent.putExtras(bundle)
            startActivity(intent)
        }
}