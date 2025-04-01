package com.example.practiceprefinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class RecyclerAdapter(
    private val students: List<Student>,
    private val listener: OnStudentActionListener
) : RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {

    interface OnStudentActionListener {
        fun onEdit(position: Int)
        fun onDelete(position: Int)
    }

    internal inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.student_id)
        val name: TextView = view.findViewById(R.id.student_name)
        val email: TextView = view.findViewById(R.id.student_email)
        val grade: TextView = view.findViewById(R.id.student_grade)

        init {
            view.setOnClickListener {
                listener.onEdit(adapterPosition)
            }

            view.setOnLongClickListener {
                listener.onDelete(adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_list_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val student = students[position]
        holder.name.text = student.name
        holder.id.text = String.format(student.id.toString())
        holder.email.text = student.email
        holder.grade.text = String.format(student.grade.toString())
    }

    override fun getItemCount(): Int = students.size
}
