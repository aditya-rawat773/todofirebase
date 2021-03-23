package com.example.todo

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_.*
import kotlinx.android.synthetic.main.item_row.*


class Fragment : Fragment(),updateANDdelete {

    lateinit var database: DatabaseReference
    var toDOList: MutableList<Notes>? = null
    lateinit var adapter: ViewAdapter
    private var listViewItem: ListView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_, container, false)
        val fab = root.findViewById<View>(R.id.fab) as FloatingActionButton
        listViewItem = root.findViewById(R.id.items_list) as ListView


        database = FirebaseDatabase.getInstance().reference


        fab.setOnClickListener { view ->
            val alert = AlertDialog.Builder(context)
            val itemEditText = EditText(context)
            alert.setMessage("Add New Item")
            alert.setTitle("Enter To Do Item Text")
            alert.setView(itemEditText)
            alert.setPositiveButton("Submit") { dialog, positiveButton ->
                val todoItemData = Notes.create()
                todoItemData.itemText = itemEditText.text.toString()
                todoItemData.done = false

                val newItemData = database.child("todo").push()
                todoItemData.UId = newItemData.key
                var x = newItemData.key
                newItemData.setValue(todoItemData)
            }
            alert.show()
        }



        toDOList = mutableListOf<Notes>()
        adapter = ViewAdapter(activity?.applicationContext!!, toDOList!!)
        listViewItem!!.adapter = adapter
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                toDOList!!.clear()
                addItemToList(snapshot)
            }
        })




        return root
    }

    private fun addItemToList(snapshot: DataSnapshot) {
        val items = snapshot.children.iterator()
        //Check if current database contains any collection
        if (items.hasNext()) {
            val toDoListindex = items.next()
            val itemsIterator = toDoListindex.children.iterator()


            while (itemsIterator.hasNext()) {

                val currentItem = itemsIterator.next()
                val todoItem = Notes.create()

                val map = currentItem.getValue() as HashMap<String, Any>

                todoItem.UId = currentItem.key
                todoItem.done = map.get("done") as Boolean?
                todoItem.itemText = map.get("itemText") as String?
                toDOList!!.add(todoItem);
            }

            adapter.notifyDataSetChanged()
        }


    }



     override fun onItemDelete(itemUID: String) {

        val itemReference = database.child("todo").child(itemUID)
        itemReference.removeValue()
        adapter.notifyDataSetChanged()
    }


}