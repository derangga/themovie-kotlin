package com.themovie.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.themovie.R

abstract class BaseActivity : AppCompatActivity() {

    fun changeActivity(activityTarget: Class<*>){
        val intent = Intent(this, activityTarget)
        startActivity(intent)
    }

    fun changeActivityTransitionBundle(activityTarget: Class<*>, bundle: Bundle, imageViewRes: ImageView){
        val intent = Intent(this, activityTarget)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
            imageViewRes, getString(R.string.transition_name))
        intent.putExtras(bundle)
        startActivity(intent, options.toBundle())
    }

    fun showToastMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun setLog(tag: String, message: String){
        Log.e(tag, message)
    }

    fun getBundle(): Bundle? {
        return intent.extras
    }
}