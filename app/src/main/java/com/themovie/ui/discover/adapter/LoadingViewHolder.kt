package com.themovie.ui.discover.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.Result
import com.aldebaran.domain.Result.Status.*
import com.aldebaran.utils.gone
import com.aldebaran.utils.visible
import com.themovie.databinding.AdapterLoadingBinding

class LoadingViewHolder(root: View, private val binding: AdapterLoadingBinding) : RecyclerView.ViewHolder(root) {

    fun bindView(loadDataState: Result.Status?, onItemErrorClick: () -> Unit){
        loadHandler(loadDataState)
        binding.txtError.setOnClickListener {
            onItemErrorClick.invoke()
        }
    }

    private fun loadHandler(loadDataState: Result.Status?){
        if(loadDataState != null && loadDataState == LOADING){
            binding.progressBar.visible()
        } else binding.progressBar.gone()

        if(loadDataState != null && loadDataState == ERROR) {
            binding.txtError.visible()
        } else binding.txtError.gone()
    }
}