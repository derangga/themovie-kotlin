package com.themovie.core.utils.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridDecoration(
    private val top: Int, private val left: Int,
    private val right: Int, private val bottom: Int,
    private val gridSize: Int
) : RecyclerView.ItemDecoration() {

    constructor(all: Int, gridSize: Int): this(all, all, all, all, gridSize)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        if (itemPosition < gridSize) {
            outRect.top = 0
        } else {
            outRect.top = top
        }

        outRect.left = left
        outRect.right = right
        outRect.bottom = bottom
    }
}