package com.sniperking.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.*

class MainActivity : AppCompatActivity() {
    var date = arrayOf(1,2,3,4,5,6)
    var myRecycleViewAdapter = MyRecycleViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var gridLayoutManager = GridLayoutManager(this,6)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return when(date.size){
                    0,1,2,3,4 ->{
                        recyclerview.layoutParams.height = resources.displayMetrics.widthPixels
                        3
                    }
                    else -> {
                        recyclerview.layoutParams.height = resources.displayMetrics.widthPixels / 3 * 2
                        2
                    }
                }
            }
        }
        recyclerview.layoutManager = gridLayoutManager
        myRecycleViewAdapter.setData(date,date.size < 5)
        recyclerview.adapter = myRecycleViewAdapter

        button.setOnClickListener {
            date = arrayOf(1,2,3)
            myRecycleViewAdapter.setData(date , date.size < 5)
        }
    }
}
