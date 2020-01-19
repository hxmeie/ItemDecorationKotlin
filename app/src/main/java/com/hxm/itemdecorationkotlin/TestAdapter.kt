package com.hxm.itemdecorationkotlin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_layout.*

/**
 * Created by hxm on 2020/1/17
 * Des:
 */
class TestAdapter : RecyclerView.Adapter<TestHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return TestHolder(view)
    }

    override fun getItemCount(): Int = 43

    override fun onBindViewHolder(holder: TestHolder, position: Int) {
        holder.tvItem.text = "$position"
        holder.tvItem.setBackgroundColor(Color.parseColor("#008577"))
    }
}

class TestHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer