package com.example.practiceprefinal

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast

class CustomListView : Activity() {
    private val students = MyApplication.getCustomStudents()
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_custom_listview)

        val addButton = findViewById<Button>(R.id.add_student)
        val back = findViewById<Button>(R.id.back)

        back.setOnClickListener {
            finish()
        }

        addButton.setOnClickListener {
            showAddDialog()
        }

        adapter = CustomAdapter(this, students)
        val listView = findViewById<ListView>(R.id.listview)

        listView.setOnItemClickListener { parent, view, position, id ->
            showEditDialog(position)
        }

        listView.setOnItemLongClickListener { parent, view, position, id ->
            showDeleteDialog(position)
            true
        }

        listView.adapter = adapter
    }

    private fun showAddDialog(){
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
        }

        val id = EditText(this).apply {
            hint = "Id"
            inputType = InputType.TYPE_CLASS_NUMBER
            layout.addView(this)
        }

        val name = EditText(this).apply {
            hint = "Name"
            layout.addView(this)
        }

        val email = EditText(this).apply {
            hint = "Email"
            inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            layout.addView(this)
        }

        val grade = EditText(this).apply {
            hint = "Grade"
            inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
            layout.addView(this)
        }

        AlertDialog.Builder(this)
            .setTitle("Add Student")
            .setIcon(android.R.drawable.ic_menu_add)
            .setView(layout)
            .setPositiveButton("Add"){ _, _ ->
                val nameInput = name.text.toString()
                val idInput = id.text.toString()
                val emailInput = email.text.toString()
                val gradeInput = grade.text.toString()

                when{
                    idInput.isEmpty() -> showToast("Enter valid ID")
                    nameInput.isEmpty() -> showToast("Enter valid Name")
                    emailInput.isEmpty() -> showToast("Enter valid Email")
                    gradeInput.isEmpty() -> showToast("Enter valid Grade")
                }

                val student = Student(idInput.toInt(), nameInput, emailInput, gradeInput.toDouble())
                students.add(student)
                MyApplication.setCustomStudents(students)
                adapter.notifyDataSetChanged()
                showToast("Student Added")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditDialog(position: Int){
        val student = students[position]
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
        }

        val id = EditText(this).apply {
            setText(String.format(student.id.toString()))
            inputType = InputType.TYPE_CLASS_NUMBER
            layout.addView(this)
        }

        val name = EditText(this).apply {
            setText(student.name)
            layout.addView(this)
        }

        val email = EditText(this).apply {
            setText(student.email)
            inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            layout.addView(this)
        }

        val grade = EditText(this).apply {
            setText(String.format(student.grade.toString()))
            inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
            layout.addView(this)
        }

        AlertDialog.Builder(this)
            .setTitle("Add Student")
            .setView(layout)
            .setIcon(android.R.drawable.ic_menu_edit)
            .setPositiveButton("Edit"){ _, _ ->
                val nameInput = name.text.toString()
                val idInput = id.text.toString()
                val emailInput = email.text.toString()
                val gradeInput = grade.text.toString()

                when{
                    idInput.isEmpty() -> showToast("Enter valid ID")
                    nameInput.isEmpty() -> showToast("Enter valid Name")
                    emailInput.isEmpty() -> showToast("Enter valid Email")
                    gradeInput.isEmpty() -> showToast("Enter valid Grade")
                }

                val editedStudent = student.copy(id = idInput.toInt(), name = nameInput, email = emailInput, grade = gradeInput.toDouble())
                students[position] = editedStudent
                MyApplication.setCustomStudents(students)
                adapter.notifyDataSetChanged()
                showToast("Student Edited")
            }
                .setNegativeButton("Cancel", null)
                .show()
    }

    private fun showDeleteDialog(position: Int){
        val student = students[position]
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete ${student.name}?")
            .setIcon(android.R.drawable.ic_delete)
            .setPositiveButton("Delete") { _, _ ->
                students.removeAt(position)
                MyApplication.setCustomStudents(students)
                adapter.notifyDataSetChanged()
                showToast("Student Deleted")
           }
            .setNegativeButton("Cancal", null)
            .show()
    }

    private fun showToast(string: String){
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }
}