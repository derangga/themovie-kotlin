package com.themovie.core.page

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.themovie.core.dialog.NoInternetBottomSheet

abstract class BaseFragment<B: ViewDataBinding> : Fragment() {

    protected lateinit var binding: B

    protected val networkErrorDialog by lazy { NoInternetBottomSheet(
        this::delegateRetryEventDialog, this::delegateOpenSetting
    )}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        onCreateViewSetup(savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onMain(savedInstanceState)
    }

    abstract fun getLayout(): Int
    abstract fun onCreateViewSetup(savedInstanceState: Bundle?)
    abstract fun onMain(savedInstanceState: Bundle?)

    fun setLog(message: String) {

    }

    fun toastMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getBundle(): Bundle? {
        return this.arguments
    }

    protected open fun delegateRetryEventDialog() {}

    protected open fun delegateOpenSetting() {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }
}