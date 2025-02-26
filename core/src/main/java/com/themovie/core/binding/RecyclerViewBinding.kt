package com.themovie.core.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.core.utils.recyclerview.GridDecoration
import com.themovie.core.utils.recyclerview.SpaceItemDecoration

@BindingAdapter(value = ["setItemSpacing", "firstItemSpacing", "setOrientation"], requireAll = true)
fun setRecyclerView(
    recyclerView: RecyclerView,
    dimens: Float,
    firstItemSpacing: Boolean,
    @RecyclerView.Orientation orientation: Int
) {
    val itemDecoration = if (orientation == RecyclerView.VERTICAL) {
        SpaceItemDecoration(
            top = dimens.toInt(),
            bottom = dimens.toInt(),
            firstItemSpacing = firstItemSpacing,
            orientation = orientation
        )
    } else {
        SpaceItemDecoration(
            right = dimens.toInt(),
            left = dimens.toInt(),
            firstItemSpacing = firstItemSpacing,
            orientation = orientation
        )
    }
    recyclerView.addItemDecoration(itemDecoration)
}

@BindingAdapter(value = ["app:gridSize"])
fun setGridMarginItemAdapter(
    recyclerView: RecyclerView,
    gridSize: Int
){
    recyclerView.addItemDecoration(GridDecoration(4, gridSize))
}