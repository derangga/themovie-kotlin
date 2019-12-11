package com.themovie.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.crashlytics.android.Crashlytics
import com.themovie.R
import io.fabric.sdk.android.Fabric

abstract class BaseActivity<B: ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        binding = DataBindingUtil.setContentView(this, getLayout())
        onActivityCreated(savedInstanceState)
    }

    protected abstract fun getLayout(): Int
    protected abstract fun onActivityCreated(savedInstanceState: Bundle?)

    fun changeActivity(activityTarget: Class<*>){
        val intent = Intent(this, activityTarget)
        startActivity(intent)
    }

    fun changeActivity(bundle: Bundle, activityTarget: Class<*>){
        val intent = Intent(this, activityTarget)
        intent.putExtras(bundle)
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