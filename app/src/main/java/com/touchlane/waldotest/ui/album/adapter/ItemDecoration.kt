package com.touchlane.waldotest.ui.album.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.touchlane.waldotest.R

class ItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val cellMargin = context.resources.getDimensionPixelSize(R.dimen.cellMargin)
        outRect.set(cellMargin, cellMargin, cellMargin, cellMargin)
    }
}