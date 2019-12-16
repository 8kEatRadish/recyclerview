package com.sniperking.myapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class MyRecycleViewAdapter(context: Context, itemOnclickListener: itemOnclickListener) : RecyclerView.Adapter<MyViewHolder>() {
    private lateinit var _date : List<Int>
    private var flag: Boolean = true
    private val context = context
    private val _itemOnclickListener = itemOnclickListener
    fun setData(data: Array<Int>){
        _date = data.toList()
        this.flag = _date.size < 5
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return _date.size
    }

    interface itemOnclickListener{
        fun itemOnclick(position: Int)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.number.text = _date[position].toString()

        if (flag){
            holder.itemView.constraintlayout_item.layoutParams.height = context.resources.displayMetrics.widthPixels / 2
            holder.itemView.constraintlayout_item.layoutParams.width = context.resources.displayMetrics.widthPixels / 2
        }else{
            holder.itemView.constraintlayout_item.layoutParams.height = context.resources.displayMetrics.widthPixels /3
            holder.itemView.constraintlayout_item.layoutParams.width = context.resources.displayMetrics.widthPixels /3
        }
        holder.itemView.setOnClickListener {
            _itemOnclickListener.itemOnclick(position)
            Log.d("suihw >> ","" + _date[position])
        }
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var number: TextView = itemView.textView
}
