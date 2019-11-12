package com.themovie.ui.discover.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.helper.LoadDataState
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Tv

class TvAdapter: PagedListAdapter<Tv, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private val DATA_VIEW = 1
    private val LOADING_VIEW = 2
    private var loadState: LoadDataState? = null
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
        return if(viewType == DATA_VIEW){
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_tv, parent, false)
            context = parent.context
            return TvViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_loading, parent, false)
            context = parent.context
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TvViewHolder){
            holder.bindView(context, getItem(position)!!, onClickAdapterListener)
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

    fun setOnClickAdapter(onClickAdapterListener: OnAdapterListener<Tv>){
        this.onClickAdapterListener = onClickAdapterListener
    }

    interface OnErrorClickListener {
        fun onClick(view: View?)
    }
}