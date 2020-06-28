package com.themovie.ui.discover.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.themovie.databinding.AdapterLoadingBinding
import com.themovie.helper.gone
import com.themovie.helper.visible
import com.themovie.restapi.Result

class LoadingViewHolder(root: View, private val binding: AdapterLoadingBinding) : RecyclerView.ViewHolder(root) {

    fun bindView(loadDataState: Result.Status?, onErrorClickListener: MovieAdapter.OnErrorClickListener){
        loadHandler(loadDataState)
        binding.txtError.setOnClickListener {
            onErrorClickListener.onClick(it)
        }
    }

    fun bindView(loadDataState: Result.Status?, onErrorClickListener: TvAdapter.OnErrorClickListener){
        loadHandler(loadDataState)
        binding.txtError.setOnClickListener {
            onErrorClickListener.onClick(it)
        }
    }

    fun bindView(loadDataState: Result.Status?, onErrorClickListener: UpcomingAdapter.OnErrorClickListener){
        loadHandler(loadDataState)
        binding.txtError.setOnClickListener {
            onErrorClickListener.onClick(it)
        }
    }

    private fun loadHandler(loadDataState: Result.Status?){
        if(loadDataState != null && loadDataState == Result.Status.LOADING){
            binding.progressBar.visible()
        } else binding.progressBar.gone()

        if(loadDataState != null && loadDataState == Result.Status.ERROR) {
            binding.txtError.visible()
        } else binding.txtError.gone()
    }
}