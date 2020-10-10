package com.aldebaran.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

inline fun <reified T: Activity> AppCompatActivity.changeActivity(){
    Intent(this, T::class.java)
        .also { startActivity(it) }
}

inline fun <reified T: Activity> AppCompatActivity.changeActivity(bundle: Bundle){
    Intent(this, T::class.java)
        .also { intent ->
            intent.putExtras(bundle)
            startActivity(intent)
        }
}

inline fun <reified T: Activity> Fragment.changeActivity(){
    Intent(requireContext(), T::class.java)
        .also { startActivity(it) }
}

inline fun <reified T: Activity> Fragment.changeActivity(bundle: Bundle){
    Intent(requireContext(), T::class.java)
        .also { intent ->
            intent.putExtras(bundle)
            startActivity(intent)
        }
}