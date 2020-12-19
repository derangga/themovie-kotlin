package com.aldebaran.utils

import android.text.Editable
import android.text.TextWatcher

class TextChangeWatcher (
    private val beforeTextChanged: ((CharSequence?, Int, Int, p3: Int) -> Unit)? = null,
    private val onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
    private val afterTextChanged: ((Editable?) -> Unit)? = null
) : TextWatcher {

    var searchFor = ""

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        beforeTextChanged?.invoke(p0, p1, p2, p3)
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        onTextChanged?.invoke(p0, p1, p2, p3)
    }

    override fun afterTextChanged(p0: Editable?) {
        afterTextChanged?.invoke(p0)
    }
}