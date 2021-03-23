package com.example.todo

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
import kotlin.coroutines.coroutineContext

class ViewAdapter (context: Context,todoList: MutableList<Notes>) : BaseAdapter() {

    private val inflater:LayoutInflater = LayoutInflater.from(context)
    private var itemList = todoList

//    var updateanddelete: updateANDdelete = context as updateANDdelete


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val UID: String = itemList.get(position).UId as String

        val itemTextData: String = itemList.get(position).itemText as String
        val done: Boolean = itemList.get(position).done as Boolean
        val view: View
        val vh: ListRowHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_row, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }
        vh.label.text = itemTextData
        vh.isDone.isChecked = done





//        vh.isDelete.setOnClickListener{
//           updateanddelete.onItemDelete(UID)
//
//
//        }
        return view
    }

    private class ListRowHolder(row: View?) {
        val label: TextView = row!!.findViewById<TextView>(R.id.tv_item_text) as TextView
        val isDone: CheckBox = row!!.findViewById<CheckBox>(R.id.cb_item_is_done) as CheckBox
        val isDelete: ImageButton = row!!.findViewById<ImageButton>(R.id.iv_cross) as ImageButton
    }


    override fun getItem(index: Int): Any {

        return itemList.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }
    override fun getCount(): Int {
        return itemList.size
    }





}