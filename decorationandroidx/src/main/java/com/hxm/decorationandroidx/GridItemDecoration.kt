package com.hxm.decorationandroidx

import android.graphics.Color
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by hxm on 2020/1/17
 * Des:
 */
class GridItemDecoration(
    private var marginStart: Int = 0,
    private var marginEnd: Int = 0,
    private var marginTop: Int = 0,
    private var marginBottom: Int = 0,
    private var margin: Int = 0,//设置margin，则marginStart,marginEnd,marginTop,marginBottom的值都为margin
    private var dividerHorSzie: Int = 2,
    private var dividerVerSize: Int = 2,
    private var horColor: Int = Color.parseColor("#ffffff"),
    private var verColor: Int = Color.parseColor("#ffffff"),
    private var marginColor: Int = Color.parseColor("#ffffff")//屏幕四周间隔颜色
) : RecyclerView.ItemDecoration() {

    init {
        if (margin != 0) {
            marginStart = margin
            marginEnd = margin
            marginTop = margin
            marginBottom = margin
        }
    }

    companion object {
        const val VERTICAL = GridLayoutManager.VERTICAL
        const val HORIZONTAL = GridLayoutManager.HORIZONTAL
    }

}