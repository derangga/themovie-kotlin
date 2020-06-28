package com.themovie.ui.discover.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.themovie.databinding.AdapterLoadingBinding
import com.themovie.databinding.AdapterTvBinding
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Tv
import com.themovie.restapi.Result

class TvAdapter: PagedListAdapter<Tv, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private val DATA_VIEW = 1
    private val LOADING_VIEW = 2
    private var loadState: Result.Status? = null
    private lateinit var context: Context
    private lateinit var onErrorClickListener: OnErrorClickListener
    private lateinit var onClickAdapterListener: OnAdapterListener<Tv>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Tv> = object: DiffUtil.ItemCallback<Tv>(){
            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.posterPath == newItem.posterPath &&
                         oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
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
            holder.bindView(loadState, onErrorClickListener)
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
        return loadState != null && loadState != Result.Status.SUCCESS
    }

    fun setLoadState(loadState: Result.Status){
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

    fun setOnErrorClickListener(onErrorClickListener: OnErrorClickListener){
        this.onErrorClickListener = onErrorClickListener
    }

    fun setOnClickAdapter(onClickAdapterListener: OnAdapterListener<Tv>){
        this.onClickAdapterListener = onClickAdapterListener
    }

    interface OnErrorClickListener {
        fun onClick(view: View?)
    }

    inner class TvViewHolder(root: View, val binding: AdapterTvBinding) : RecyclerView.ViewHolder(root) {
        fun onTvAdapterClick(view: View, tv: Tv){
            onClickAdapterListener.onClick(view, tv)
        }
    }
}