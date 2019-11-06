package com.themovie.ui.discover.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Upcoming

class UpcomingAdapter: PagedListAdapter<Upcoming, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private val DATA_VIEW = 1
    private val LOADING_VIEW = 2
    private var loadState: LoadDataState? = null
    private lateinit var context: Context
    private lateinit var onErrorClickListener: OnErrorClickListener
    private lateinit var onClickAdapterListener: OnClickAdapterListener

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
        return if(viewType == DATA_VIEW){
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_movies, parent, false)
            context = parent.context
            UpcomingViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_loading, parent, false)
            context = parent.context
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is UpcomingViewHolder){
            holder.bindView(context, getItem(position)!!, onClickAdapterListener)
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
        return loadState != null && loadState != LoadDataState.LOADED
    }

    fun setLoadState(loadState: LoadDataState){
        val previousState = this.loadState;
        val previousExtraRow = hasFooter()
        this.loadState = loadState
        val newExtraRow = hasFooter();
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

    fun setOnClickAdapter(onClickAdapterListener: OnClickAdapterListener){
        this.onClickAdapterListener = onClickAdapterListener
    }

    interface OnErrorClickListener {
        fun onClick(view: View?)
    }

    interface OnClickAdapterListener {
        fun onItemClick(view: View?, upcoming: Upcoming)
    }

}