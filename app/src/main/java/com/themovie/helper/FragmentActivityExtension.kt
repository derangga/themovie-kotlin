package com.themovie.helper

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.themovie.ui.bottomsheet.NoInternetBottomSheet

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