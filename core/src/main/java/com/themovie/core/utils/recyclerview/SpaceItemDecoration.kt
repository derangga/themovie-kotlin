package com.themovie.core.utils.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration (
    private val left: Int = 0, private val top: Int = 0,
    private val right: Int = 0, private val bottom: Int = 0,
    private val firstItemSpacing: Boolean = false,
    @RecyclerView.Orientation private val orientation: Int = RecyclerView.VERTICAL
): RecyclerView.ItemDecoration() {

    constructor(
        all: Int,
        firstItemSpacing: Boolean = false,
        @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL
    ): this(all, all, all, all, firstItemSpacing, orientation)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val isFirstItem = parent.getChildAdapterPosition(view) == 0

        if (orientation == RecyclerView.VERTICAL) {
            outRect.apply {
                left = this@SpaceItemDecoration.left
                top = if (firstItemSpacing && isFirstItem) this@SpaceItemDecoration.top else 0
                right = this@SpaceItemDecoration.right
                bottom = this@SpaceItemDecoration.bottom
            }
        } else {
            outRect.apply {
                left = if (firstItemSpacing && isFirstItem) this@SpaceItemDecoration.left else 0
                top = this@SpaceItemDecoration.top
                right = this@SpaceItemDecoration.right
                bottom = this@SpaceItemDecoration.bottom
            }
        }
    }
}