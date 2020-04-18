package com.hxm.itemdecorationkotlin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_layout2.*

/**
 * Created by hxm on 2020/1/17
 * Des:
 */
class TestAdapter2 : RecyclerView.Adapter<TestHolder2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder2 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout2, parent, false)
        return TestHolder2(view)
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: TestHolder2, position: Int) {
        holder.tvItem.text = "$position"
        holder.tvItem.setBackgroundColor(Color.parseColor("#008577"))
    }
}

class TestHolder2(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer