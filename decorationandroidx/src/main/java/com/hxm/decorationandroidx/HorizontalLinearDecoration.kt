package com.hxm.decorationandroidx

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by hxm on 2020/4/16
 * Des:itemDecoration
 */
class HorizontalLinearDecoration(
    private val startAndEndDividerSize: Float,
    private val dividerSize: Float,
    private var dividerColor: Int = Color.WHITE//颜色
) : RecyclerView.ItemDecoration() {
    private val paint: Paint by lazy {
        Paint().apply {
            color = dividerColor
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let {
            val childCount = parent.childCount
            val itemCount = it.itemCount
            for (i in 0 until childCount) {
                val childView = parent.getChildAt(i)
                val itemPosition = parent.getChildLayoutPosition(childView)
                val left = if (itemPosition == 0) {
                    0f
                } else {
                    childView.left - dividerSize
                }
                val right = childView.left.toFloat()
                val top = childView.top.toFloat()
                val bottom = childView.bottom.toFloat()
                c.drawRect(left, top, right, bottom, paint)
                if (itemPosition == itemCount - 1) {
                    val lastLeft = childView.right.toFloat()
                    val lastRight = lastLeft + startAndEndDividerSize
                    c.drawRect(lastLeft, top, lastRight, bottom, paint)
                }
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.adapter?.let {
            val position = parent.getChildLayoutPosition(view)
            val childCount = it.itemCount
            val left = if (position == 0) {
                startAndEndDividerSize.toInt()
            } else {
                dividerSize.toInt()
            }
            val right = if (position == childCount - 1) {
                startAndEndDividerSize.toInt()
            } else {
                0
            }
            outRect.set(left, 0, right, 0)
        }
    }
}