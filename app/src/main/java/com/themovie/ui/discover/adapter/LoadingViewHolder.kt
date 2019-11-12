package com.themovie.ui.discover.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.themovie.helper.LoadDataState
import kotlinx.android.synthetic.main.adapter_loading.view.*

class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(loadDataState: LoadDataState?, onErrorClickListener: MovieAdapter.OnErrorClickListener){
        loadHandler(loadDataState)
        itemView.txt_error.setOnClickListener {
            onErrorClickListener.onClick(it)
        }
    }

    fun bindView(loadDataState: LoadDataState?, onErrorClickListener: TvAdapter.OnErrorClickListener){
        loadHandler(loadDataState)
        itemView.txt_error.setOnClickListener {
            onErrorClickListener.onClick(it)
        }
    }

    fun bindView(loadDataState: LoadDataState?, onErrorClickListener: UpcomingAdapter.OnErrorClickListener){
        loadHandler(loadDataState)
        itemView.txt_error.setOnClickListener {
            onErrorClickListener.onClick(it)
        }
    }

    private fun loadHandler(loadDataState: LoadDataState?){
        if(loadDataState != null && loadDataState == LoadDataState.LOADING){
            itemView.progress_bar.visibility = View.VISIBLE
        } else itemView.progress_bar.visibility = View.GONE

        if(loadDataState != null && loadDataState == LoadDataState.ERROR) {
            itemView.txt_error.visibility = View.VISIBLE
        } else itemView.txt_error.visibility = View.GONE

    }

}