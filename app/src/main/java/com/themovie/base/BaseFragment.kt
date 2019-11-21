package com.themovie.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Fabric.with(context, Crashlytics())
        onMain(savedInstanceState)
    }

    abstract fun onMain(savedInstanceState: Bundle?)

    fun changeActivity(activityTarget: Class<*>){
        val intent = Intent(context, activityTarget)
        startActivity(intent)
    }

    fun changeActivity(bundle: Bundle, activityTarget: Class<*>){
        val intent = Intent(context, activityTarget)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun setLog(tag: String, message: String){
        Log.e(tag, message)
    }

    fun showToastMessage(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getBundle(): Bundle? {
        return this.arguments
    }
}