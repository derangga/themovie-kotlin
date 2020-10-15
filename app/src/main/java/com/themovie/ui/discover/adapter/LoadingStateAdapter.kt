package com.themovie.ui.discover.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.databinding.AdapterLoadingStateBinding

class LoadingStateAdapter (
    private val retry: () -> Unit
): LoadStateAdapter<LoadingStateAdapter.LoadViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterLoadingStateBinding
            .inflate(inflater, parent, false)
        return LoadViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        holder.binding.apply {
            progressBar.isVisible = loadState is LoadState.Loading
            txtError.isVisible = loadState !is LoadState.Loading
        }
    }

    inner class LoadViewHolder(
        itemView: View,
        val binding: AdapterLoadingStateBinding
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            binding.txtError.setOnClickListener { retry.invoke() }
        }
    }
}