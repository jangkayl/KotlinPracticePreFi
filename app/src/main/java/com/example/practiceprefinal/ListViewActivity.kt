package com.example.practiceprefinal

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class ListViewActivity : Activity() {
    private lateinit var adapter: ArrayAdapter<String>
    private val students = MyApplication.getSimpleStudents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview_activity)

        val addButton = findViewById<Button>(R.id.add_student)
        val backButton = findViewById<Button>(R.id.back)

        addButton.setOnClickListener {
            showAddDialog()
        }

        backButton.setOnClickListener {
            finish()
        }

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            students
        )

        val listView = findViewById<ListView>(R.id.listview)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            showEditDialog(position)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            showDeleteDialog(position)
            true
        }
    }

    private fun showAddDialog() {
        val input = EditText(this)

        AlertDialog.Builder(this)
            .setTitle("Add Students")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                if(input.text.isNotEmpty()){
                    students.add(0, input.text.toString())
                    MyApplication.setSimpleStudents(students)
                    Toast.makeText(this, "Student Added", Toast.LENGTH_SHORT).show()
                    adapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancal", null)
            .setIcon(android.R.drawable.ic_menu_add)
            .show()
    }

    private fun showEditDialog(position: Int){
        val edit = EditText(this)
        edit.setText(students[position])

        AlertDialog.Builder(this)
            .setTitle("Edit Student")
            .setView(edit)
            .setPositiveButton("Save"){ _, _ ->
                edit.text.toString().takeIf { it.isNotEmpty() }?.let {
                    students[position] = it
                    MyApplication.setSimpleStudents(students)
                    adapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancel", null)
            .setIcon(android.R.drawable.ic_menu_edit)
            .show()
    }

    private fun showDeleteDialog(position: Int){
        val delete = TextView(this)
        delete.text = students[position]

        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setView(delete)
            .setPositiveButton("Delete") { _, _ ->
                students.remove(students[position])
                MyApplication.setSimpleStudents(students)
                Toast.makeText(this, "Student successfully deleted", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .setIcon(android.R.drawable.ic_delete)
            .show()
    }
}