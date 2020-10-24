package com.aldebaran.core

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import timber.log.Timber

abstract class BaseActivity<B: ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayout())
        onActivityCreated(savedInstanceState)
    }

    protected abstract fun getLayout(): Int
    protected abstract fun onActivityCreated(savedInstanceState: Bundle?)

    fun showToastMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun setLog(message: String){
        Timber.e(message)
    }

    fun getBundle(): Bundle? {
        return intent.extras
    }
}
