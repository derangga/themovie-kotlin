package com.themovie.ui.discover.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.themovie.databinding.AdapterLoadingBinding
import com.themovie.databinding.AdapterUpcomingBinding
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Upcoming
import com.themovie.restapi.Result

class UpcomingAdapter: PagedListAdapter<Upcoming, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private val DATA_VIEW = 1
    private val LOADING_VIEW = 2
    private var loadState: Result.Status? = null
    private lateinit var context: Context
    private lateinit var onErrorClickListener: OnErrorClickListener
    private lateinit var onClickAdapterListener: OnAdapterListener<Upcoming>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Upcoming> = object: DiffUtil.ItemCallback<Upcoming>(){
            override fun areItemsTheSame(oldItem: Upcoming, newItem: Upcoming): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Upcoming, newItem: Upcoming): Boolean {
                return oldItem.title == newItem.title &&
                        oldItem.posterPath == newItem.posterPath &&
                        oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        return if(viewType == DATA_VIEW){
            val view = AdapterUpcomingBinding
                .inflate(inflater, parent, false)
            UpcomingViewHolder(view.root, view)
        } else {
            val view = AdapterLoadingBinding
                .inflate(inflater, parent, false)
            LoadingViewHolder(view.root, view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is UpcomingViewHolder){
            holder.binding.upcoming = getItem(position)
            holder.binding.vh = holder
        }else if(holder is LoadingViewHolder){
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

    fun setOnClickAdapter(onClickAdapterListener: OnAdapterListener<Upcoming>){
        this.onClickAdapterListener = onClickAdapterListener
    }

    interface OnErrorClickListener {
        fun onClick(view: View?)
    }

    inner class UpcomingViewHolder(root: View, val binding: AdapterUpcomingBinding) : RecyclerView.ViewHolder(root){
        fun onUpcomingClick(view: View, data: Upcoming){
            onClickAdapterListener.onClick(view, data)
        }
    }
}