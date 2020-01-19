package com.hxm.itemdecorationkotlin

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.hxm.decorationandroidx.GridItemDecoration
import kotlinx.android.synthetic.main.activity_decoration.*

/**
 * Created by hxm on 2020/1/19
 * Des:
 */
class GridDecorationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decoration)

        recyclerView.apply {
            addItemDecoration(
                GridItemDecoration(
                    dividerColor = ContextCompat.getColor(
                        this@GridDecorationActivity,
                        R.color.orange
                    ),
                    dividerSize = 10,
                    margin = 10,
                    marginColor = Color.RED
                )
            )
            layoutManager = GridLayoutManager(this@GridDecorationActivity, 4)
            adapter = TestAdapter()
        }
    }
}