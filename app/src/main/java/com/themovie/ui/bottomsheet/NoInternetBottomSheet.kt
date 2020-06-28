package com.themovie.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.themovie.databinding.BottomSheetNetworkBinding
import com.themovie.helper.gone

class NoInternetBottomSheet(private val hideRetry: Boolean): BottomSheetDialogFragment() {

    private lateinit var binding:BottomSheetNetworkBinding
    private var listener: ButtonListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetNetworkBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnClose.setOnClickListener { dismiss() }
            btnRetry.setOnClickListener {
                dismiss()
                listener?.onRetry()
            }
            btnSetting.setOnClickListener {
                dismiss()
                listener?.onSetting()
            }
            if(hideRetry) binding.btnRetry.gone()

        }
    }

    fun setListener(listener: ButtonListener) {
        this.listener = listener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun hideRetryButton(){
        binding.btnRetry.gone()
    }

    interface ButtonListener{
        fun onRetry()
        fun onSetting()
    }
}