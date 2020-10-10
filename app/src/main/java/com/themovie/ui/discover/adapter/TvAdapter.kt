package com.themovie.ui.discover.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.Result.*
import com.aldebaran.domain.Result.Status.SUCCESS
import com.aldebaran.domain.entities.remote.TvResponse
import com.themovie.databinding.AdapterLoadingBinding
import com.themovie.databinding.AdapterTvBinding

class TvAdapter (
    private val onItemClick: (TvResponse) -> Unit,
    private val onItemErrorClick: () -> Unit
): PagedListAdapter<TvResponse, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var loadState: Status? = null

    companion object{

        private const val DATA_VIEW = 1
        private const val LOADING_VIEW = 2

        val DIFF_CALLBACK: DiffUtil.ItemCallback<TvResponse> = object: DiffUtil.ItemCallback<TvResponse>(){
            override fun areItemsTheSame(oldItem: TvResponse, newItem: TvResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvResponse, newItem: TvResponse): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.posterPath == newItem.posterPath &&
                         oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if(viewType == DATA_VIEW){
            val view = AdapterTvBinding
                .inflate(inflater, parent, false)
            return TvViewHolder(view.root, view)
        } else {
            val view = AdapterLoadingBinding
                .inflate(inflater, parent, false)
            LoadingViewHolder(view.root, view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TvViewHolder){
            holder.binding.tv = getItem(position)
            holder.binding.vh = holder
        } else if(holder is LoadingViewHolder){
            holder.bindView(loadState, onItemErrorClick)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(hasFooter() && position == itemCount - 1) LOADING_VIEW
        else DATA_VIEW
    }

    override fun getItemCount(): Int {
        return if(hasFooter()) super.getItemCount() + 1
        else super.getItemCount()

    }

    private fun hasFooter(): Boolean {
        return loadState != null && loadState != SUCCESS
    }

    fun setLoadState(loadState: Status){
        val previousState = this.loadState
        val previousExtraRow = hasFooter()
        this.loadState = loadState
        val newExtraRow = hasFooter()
        if(previousExtraRow != newExtraRow){
            if(previousExtraRow) notifyItemRemoved(super.getItemCount())
            else notifyItemInserted(super.getItemCount())
        } else if (newExtraRow && previousState != loadState){
            notifyItemChanged(itemCount - 1)
        }
    }

    interface OnErrorClickListener {
        fun onClick(view: View?)
    }

    inner class TvViewHolder(root: View, val binding: AdapterTvBinding) : RecyclerView.ViewHolder(root) {
        fun onTvAdapterClick(tv: TvResponse){
            onItemClick.invoke(tv)
        }
    }
}