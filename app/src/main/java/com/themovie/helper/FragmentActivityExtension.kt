package com.themovie.helper

import androidx.fragment.app.Fragment

@Deprecated("will be remove")
fun Fragment.showNetworkError(hideRetry: Boolean, action: () -> Unit){
//    val bottom = NoInternetBottomSheet(hideRetry)
//    bottom.setListener(object: NoInternetBottomSheet.ButtonListener{
//        override fun onRetry() {
//            action()
//        }
//
//        override fun onSetting() {
//            startActivity(Intent(Settings.ACTION_SETTINGS))
//        }
//    })
//    bottom.show(childFragmentManager, "")
}