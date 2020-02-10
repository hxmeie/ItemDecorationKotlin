package com.hxm.decorationandroidx

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by hxm on 2020/1/17
 * Des:
 */
class GridItemDecoration(
    private var marginLeft: Int = 0,
    private var marginRight: Int = 0,
    private var marginTop: Int = 0,
    private var marginBottom: Int = 0,
    private var margin: Int = -1,//设置margin，则覆盖marginStart,marginEnd,marginTop,marginBottom的值
    private var dividerHorSize: Int = 2,//横向线宽度
    private var dividerVerSize: Int = 2,//纵向线宽度
    private var dividerSize: Int = -1,//设置dividerSize，则覆盖horSize和verSize的值
    private var orientation: Int = VERTICAL,//没处理横向滑动呢
    private var horColor: Int = Color.parseColor("#ffffff"),//横向线颜色
    private var verColor: Int = Color.parseColor("#ffffff"),//纵向线颜色
    private var dividerColor: Int = -1,//设置dividerColor，则覆盖horColor和verColor的值
    private var marginColor: Int = Color.parseColor("#ffffff")//屏幕四周间隔颜色
) : RecyclerView.ItemDecoration() {

    companion object {
        const val VERTICAL = GridLayoutManager.VERTICAL
        const val HORIZONTAL = GridLayoutManager.HORIZONTAL
    }

    private val mVerPaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = verColor
        }
    }
    private val mHorPaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = horColor
        }
    }
    private val mMarginPaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = marginColor
        }
    }

    init {
        if (margin != -1) {
            marginLeft = margin
            marginRight = margin
            marginTop = margin
            marginBottom = margin
        }
        if (dividerSize != -1) {
            dividerHorSize = dividerSize
            dividerVerSize = dividerSize
        }

        if (dividerColor != -1) {
            horColor = dividerColor
            verColor = dividerColor
        }
    }

    /**
     * 需要注意的一点是 getItemOffsets 是针对每一个 ItemView，而 onDraw 方法却是针对 RecyclerView 本身，
     * 所以在 onDraw 方法中需要遍历屏幕上可见的 ItemView，分别获取它们的位置信息，然后分别的绘制对应的分割线。
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        parent.adapter?.let {
            val childCount = parent.childCount
            val itemCount = it.itemCount
            val spanCount = getSpanCount(parent)
            drawHorizontal(c, parent, childCount, itemCount, spanCount)
            drawVertical(c, parent, childCount, spanCount)
            drawMarginLeftAndRight(c, parent, childCount, itemCount, spanCount)
            drawMarginTopAndBottom(c, parent, childCount, itemCount, spanCount)
        }
    }

    /**
     * 每一个itemView的（left+right）相等且（top+bottom）也相等，这样才能保证itemView大小都一样
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.adapter?.let {
            val itemCount = it.itemCount
            val spanCount = getSpanCount(parent)
            val itemPosition = parent.getChildLayoutPosition(view)
            val column = itemPosition % spanCount
            val bottom = if (isLastRow(itemPosition, spanCount, itemCount)) 0 else dividerHorSize
            val left = column * dividerVerSize / spanCount
            val right = dividerVerSize - (column + 1) * dividerVerSize / spanCount
            outRect.set(left, 0, right, bottom)
            marginLeftAndRightOffsets(outRect, spanCount, itemPosition)
            marginTopAndBottomOffsets(outRect, spanCount, itemPosition, itemCount)
        }
    }

    private fun marginLeftAndRightOffsets(outRect: Rect, spanCount: Int, itemPosition: Int) {
        if (marginLeft <= 0 && marginRight <= 0)
            return
        val average = (marginLeft + marginRight) / spanCount
        outRect.left += marginLeft - (itemPosition % spanCount) * average
        outRect.right += (itemPosition % spanCount + 1) * average - marginLeft
    }

    private fun marginTopAndBottomOffsets(
        outRect: Rect,
        spanCount: Int,
        itemPosition: Int,
        itemCount: Int
    ) {
        if (marginTop <= 0 && marginBottom <= 0)
            return
        if (isFrostRow(itemPosition, spanCount))
            outRect.top += marginTop
        if (isLastRow(itemPosition, spanCount, itemCount))
            outRect.bottom += marginBottom
    }

    private fun drawHorizontal(
        canvas: Canvas,
        parent: RecyclerView,
        childCount: Int,
        itemCount: Int,
        spanCount: Int
    ) {
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildLayoutPosition(child)
            if (isLastRow(position, spanCount, itemCount))
                return
            val left = child.left
            val top = child.bottom
            val right = if (isLastColumn(
                    position,
                    spanCount,
                    childCount
                )
            ) child.right else child.right + dividerVerSize
            val bottom = child.bottom + dividerHorSize
            canvas.drawRect(
                left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                mHorPaint
            )
        }
    }

    private fun drawVertical(
        canvas: Canvas,
        parent: RecyclerView,
        childCount: Int,
        spanCount: Int
    ) {
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildLayoutPosition(child)
            if (isLastColumn(position, spanCount, childCount))
                continue
            val left = child.right
            val top = child.top
            val right = child.right + dividerVerSize
            val bottom = child.bottom
            canvas.drawRect(
                left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                mVerPaint
            )
        }
    }

    private fun drawMarginLeftAndRight(
        canvas: Canvas,
        parent: RecyclerView,
        childCount: Int,
        itemCount: Int,
        spanCount: Int
    ) {
        if (marginLeft <= 0 && marginRight <= 0)
            return
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildLayoutPosition(child)
            if (isFirstColumn(position, spanCount)) {
                val l = child.left - marginLeft
                val t = child.top - marginTop
                val r = child.left
                val b = if (isLastRow(position, spanCount, itemCount))
                    child.bottom + marginBottom else child.bottom + dividerHorSize
                canvas.drawRect(l.toFloat(), t.toFloat(), r.toFloat(), b.toFloat(), mMarginPaint)
            }
            if (isLastColumn(position, spanCount, childCount)) {
                val l = child.right
                val t = child.top - marginTop
                val r = child.right + marginRight
                val b = if (isLastRow(position, spanCount, itemCount))
                    child.bottom + marginBottom else child.bottom + dividerHorSize
                canvas.drawRect(l.toFloat(), t.toFloat(), r.toFloat(), b.toFloat(), mMarginPaint)
            }
        }
    }

    private fun drawMarginTopAndBottom(
        canvas: Canvas,
        parent: RecyclerView,
        childCount: Int,
        itemCount: Int,
        spanCount: Int
    ) {
        if (marginTop <= 0 && marginBottom <= 0)
            return
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildLayoutPosition(child)
            if (isFrostRow(position, spanCount)) {
                val l = child.left
                val t = child.top - marginTop
                val r = if (isLastColumn(position, spanCount, childCount))
                    child.right else child.right + dividerVerSize
                val b = child.top
                canvas.drawRect(l.toFloat(), t.toFloat(), r.toFloat(), b.toFloat(), mMarginPaint)
            }
            if (isLastRow(position, spanCount, itemCount)) {
                val l = child.left
                val t = child.bottom
                val r = if (isLastColumn(position, spanCount, childCount))
                    child.right else child.right + dividerVerSize
                val b = child.bottom + marginBottom
                canvas.drawRect(l.toFloat(), t.toFloat(), r.toFloat(), b.toFloat(), mMarginPaint)
            }
        }
    }

    private fun getSpanCount(parent: RecyclerView): Int {
        var spanCount = -1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager.spanCount
        }
        return spanCount
    }

    /**
     * 是否是第一行
     */
    private fun isFrostRow(position: Int, spanCount: Int): Boolean {
        return if (orientation == VERTICAL) position < spanCount else position % spanCount == 0
    }

    /**
     * 是否是最后一行
     */
    private fun isLastRow(position: Int, spanCount: Int, childCount: Int): Boolean {
        return if (orientation == VERTICAL) {
            var lastRowCount = childCount % spanCount
            lastRowCount = if (lastRowCount == 0) spanCount else lastRowCount
            position >= childCount - lastRowCount
        } else {
            (position + 1) % spanCount == 0
        }
    }

    /**
     * 是否是第一列
     */
    private fun isFirstColumn(position: Int, spanCount: Int): Boolean {
        return if (orientation == VERTICAL) position % spanCount == 0 else position < spanCount
    }

    /**
     * 是否是最后一列
     */
    private fun isLastColumn(position: Int, spanCount: Int, childCount: Int): Boolean {
        return if (orientation == VERTICAL) {
            (position + 1) % spanCount == 0
        } else {
            var lastColumnCount = childCount % spanCount
            lastColumnCount = if (lastColumnCount == 0) spanCount else lastColumnCount
            position >= childCount - lastColumnCount
        }
    }
}