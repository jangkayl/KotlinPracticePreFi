package com.example.practiceprefinal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CustomAdapter(private val context: Context, private val students: MutableList<Student>) : BaseAdapter() {
    override fun getCount(): Int = students.size

    override fun getItem(position: Int): Any = students[position]

    override fun getItemId(position: Int): Long = students[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.student_list_view, parent, false)

        val studentId = view.findViewById<TextView>(R.id.student_id)
        val studentName = view.findViewById<TextView>(R.id.student_name)
        val studentEmail = view.findViewById<TextView>(R.id.student_email)
        val studentGrade = view.findViewById<TextView>(R.id.student_grade)

        studentId.text = String.format(students[position].id.toString())
        studentName.text = students[position].name
        studentEmail.text = students[position].email
        studentGrade.text = String.format(students[position].grade.toString())

        return view
    }
}