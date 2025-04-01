package com.example.practiceprefinal

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerActivity : Activity() {
    private val students = MyApplication.getCustomStudents()
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_lists)

        val addButton = findViewById<Button>(R.id.add_student)
        val back = findViewById<Button>(R.id.back)

        back.setOnClickListener { finish() }
        addButton.setOnClickListener { showAddDialog() }

        val recycler = findViewById<RecyclerView>(R.id.recyclerview)
        adapter = RecyclerAdapter(students, object : RecyclerAdapter.OnStudentActionListener {
            override fun onEdit(position: Int) {
                showEditDialog(position)
            }

            override fun onDelete(position: Int) {
                showDeleteDialog(position)
            }
        })

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    private fun showAddDialog() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
        }

        val id = EditText(this).apply {
            hint = "Id"
            inputType = InputType.TYPE_CLASS_NUMBER
        }
        layout.addView(id)

        val name = EditText(this).apply {
            hint = "Name"
        }
        layout.addView(name)

        val email = EditText(this).apply {
            hint = "Email"
            inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        layout.addView(email)

        val grade = EditText(this).apply {
            hint = "Grade"
            inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
        }
        layout.addView(grade)

        AlertDialog.Builder(this)
            .setTitle("Add Student")
            .setIcon(android.R.drawable.ic_menu_add)
            .setView(layout)
            .setPositiveButton("Add") { _, _ ->
                val idInput = id.text.toString()
                val nameInput = name.text.toString()
                val emailInput = email.text.toString()
                val gradeInput = grade.text.toString()

                if (idInput.isEmpty() || nameInput.isEmpty() || emailInput.isEmpty() || gradeInput.isEmpty()) {
                    showToast("Please enter valid data")
                    return@setPositiveButton
                }

                val student = Student(idInput.toInt(), nameInput, emailInput, gradeInput.toDouble())
                students.add(student)
                MyApplication.setCustomStudents(students)
                adapter.notifyItemInserted(students.size - 1)
                showToast("Student Added")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditDialog(position: Int) {
        val student = students[position]
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
        }

        val id = EditText(this).apply {
            setText(String.format(student.id.toString()))
            inputType = InputType.TYPE_CLASS_NUMBER
        }
        layout.addView(id)

        val name = EditText(this).apply {
            setText(student.name)
        }
        layout.addView(name)

        val email = EditText(this).apply {
            setText(student.email)
            inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        layout.addView(email)

        val grade = EditText(this).apply {
            setText(String.format(student.grade.toString()))
            inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
        }
        layout.addView(grade)

        AlertDialog.Builder(this)
            .setTitle("Edit Student")
            .setView(layout)
            .setIcon(android.R.drawable.ic_menu_edit)
            .setPositiveButton("Edit") { _, _ ->
                val idInput = id.text.toString()
                val nameInput = name.text.toString()
                val emailInput = email.text.toString()
                val gradeInput = grade.text.toString()

                if (idInput.isEmpty() || nameInput.isEmpty() || emailInput.isEmpty() || gradeInput.isEmpty()) {
                    showToast("Please enter valid data")
                    return@setPositiveButton
                }

                students[position] = student.copy(
                    id = idInput.toInt(),
                    name = nameInput,
                    email = emailInput,
                    grade = gradeInput.toDouble()
                )
                MyApplication.setCustomStudents(students)
                adapter.notifyItemChanged(position)
                showToast("Student Edited")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteDialog(position: Int) {
        val student = students[position]
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete ${student.name}?")
            .setIcon(android.R.drawable.ic_delete)
            .setPositiveButton("Delete") { _, _ ->
                students.removeAt(position)
                MyApplication.setCustomStudents(students)
                adapter.notifyItemRemoved(position)
                showToast("Student Deleted")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }
}
