package com.hxm.itemdecorationkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hxm.decorationandroidx.HorizontalLinearDecoration
import kotlinx.android.synthetic.main.activity_hor_decoration.*


/**
 * Created by hxm on 2020/1/17
 * Des:
 */
class HorizontalLinearDecorationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hor_decoration)
        with(recyclerView1) {
            addItemDecoration(
                HorizontalLinearDecoration(60f, 40f)
            )
            layoutManager = LinearLayoutManager(
                this@HorizontalLinearDecorationActivity,
                LinearLayoutManager.HORIZONTAL, false
            )
            adapter = TestAdapter2()
        }
    }
}