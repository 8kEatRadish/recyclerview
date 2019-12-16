package com.sniperking.myapplication

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.MeasureSpec.getSize
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.*
import kotlinx.android.synthetic.main.item.view.*

class MainActivity : AppCompatActivity() {
    private var date: Array<Int> = arrayOf(1, 2, 3, 4, 5, 6)
    private var bigitemData = 0
    private var myRecycleViewAdapter = MyRecycleViewAdapter(this , object : MyRecycleViewAdapter.itemOnclickListener{
        override fun itemOnclick(position: Int) {
            bigitem.textView.text = date[position].toString()
            bigitemData = position
            bigitem.visibility = View.VISIBLE
            var scaleX = ObjectAnimator.ofFloat(bigitem,"scaleX", 0f,1f)
            var scaleY = ObjectAnimator.ofFloat(bigitem,"scaleY", 0f,1f)
            scaleX.duration = 100
            scaleY.duration = 100
            scaleX.start()
            scaleY.start()
        }
    })
    private val _context = this

    private var spanSizeLookup = object : SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (date.size) {
                0, 1, 2, 3, 4 -> {
                    3
                }
                else -> {
                    2
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var gridLayoutManager = object : GridLayoutManager(this, 6){
            override fun onMeasure(
                recycler: RecyclerView.Recycler,
                state: RecyclerView.State,
                widthSpec: Int,
                heightSpec: Int
            ) {
                super.onMeasure(recycler, state, widthSpec, heightSpec)
                var measuredWidth = getSize(widthSpec)
                var measuredHeight : Int
                if (date.size < 5){
                    measuredHeight = _context.resources.displayMetrics.widthPixels
                }else{
                    measuredHeight = _context.resources.displayMetrics.widthPixels / 3 * 2
                }
                setMeasuredDimension(measuredWidth, measuredHeight)
            }

            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        gridLayoutManager.spanSizeLookup = spanSizeLookup
        recyclerview.layoutManager = gridLayoutManager
        myRecycleViewAdapter.setData(date)
        recyclerview.adapter = myRecycleViewAdapter
        button.setOnClickListener {
            if (date.size <= 2){
                Toast.makeText(this,"不能在少了",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            deleteItem(date.size - 1)
            myRecycleViewAdapter.setData(date)
        }
        button2.setOnClickListener {
            if (date.size >= 6){
                Toast.makeText(this,"不能在多了",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            addItem((0..10).random())
            myRecycleViewAdapter.setData(date)
        }
        bigitem.setOnClickListener {
            bigitem.clearAnimation()
            bigitem.visibility = View.GONE
        }
    }
    private fun deleteItem(index:Int){
        var newArr = Array(date.size - 1){ 0}
        for(i in newArr.indices){
            if(i < index){
                newArr[i] = date[i]
            }else{
                newArr[i] = date[i + 1]
            }
        }
        if (index == bigitemData){
            bigitem.visibility = View.GONE
        }
        date = newArr
    }

    private fun addItem(item:Int){
        var newArr = Array(date.size + 1) { 0}
        for(i in date.indices){
            newArr[i] = date[i]
        }
        newArr[date.size] = item
        date = newArr
    }
}
