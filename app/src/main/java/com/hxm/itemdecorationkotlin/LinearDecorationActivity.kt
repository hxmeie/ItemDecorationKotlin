package com.hxm.itemdecorationkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hxm.decorationandroidx.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_decoration.*

/**
 * Created by hxm on 2020/1/17
 * Des:
 */
class LinearDecorationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decoration)
        with(recyclerView) {
            addItemDecoration(
                LinearItemDecoration(
                    dividerColor = ContextCompat.getColor(
                        this@LinearDecorationActivity,
                        R.color.orange
                    ),
                    dividerSize = 10
                )
            )
            layoutManager = LinearLayoutManager(this@LinearDecorationActivity)
            adapter = TestAdapter()
        }
    }
}