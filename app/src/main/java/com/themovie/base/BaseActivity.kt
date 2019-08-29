package com.themovie.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getView())
        setOnMain(savedInstanceState)
    }

    abstract fun getView(): Int
    abstract fun setOnMain(savedInstanceState: Bundle?)

    fun changeActivity(activityTarget: Class<*>){
        val intent = Intent(this, activityTarget)
        startActivity(intent)
    }

    fun showToastMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun setLog(tag: String, message: String){
        Log.e(tag, message)
    }
}