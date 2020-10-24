package com.aldebaran.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aldebaran.core.databinding.BottomSheetNetworkBinding
import com.aldebaran.utils.gone
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NoInternetBottomSheet(
    private val onRetryEvent: () -> Unit,
    private val onSettingEvent: () -> Unit,
    private var hideRetry: Boolean = false,
    private val cancelable: Boolean = false
): BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetNetworkBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetNetworkBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        isCancelable = cancelable
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnClose.setOnClickListener { dismiss() }
            btnRetry.setOnClickListener {
                dismiss()
                onRetryEvent.invoke()
            }
            btnSetting.setOnClickListener {
                dismiss()
                onSettingEvent.invoke()
            }

            if(hideRetry) binding.btnRetry.gone()
        }
    }

    fun hideRetry() {
        hideRetry = true
    }
}