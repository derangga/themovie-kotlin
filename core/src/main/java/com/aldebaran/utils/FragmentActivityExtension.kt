package com.aldebaran.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

inline fun <reified T: Activity> AppCompatActivity.changeActivity(){
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

inline fun <reified T: Activity> AppCompatActivity.changeActivity(bundle: Bundle){
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundle)
    startActivity(intent)
}

inline fun <reified T: Activity> Fragment.changeActivity(){
    val intent = Intent(requireContext(), T::class.java)
    startActivity(intent)
}

inline fun <reified T: Activity> Fragment.changeActivity(bundle: Bundle){
    val intent = Intent(requireContext(), T::class.java)
    intent.putExtras(bundle)
    startActivity(intent)
}