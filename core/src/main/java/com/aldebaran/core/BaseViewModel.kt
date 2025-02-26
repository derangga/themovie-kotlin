package com.aldebaran.core

import androidx.lifecycle.ViewModel
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    protected fun setLog(message: String) {
        Timber.e(message)
    }
}