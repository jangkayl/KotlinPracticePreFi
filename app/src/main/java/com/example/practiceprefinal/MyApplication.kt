package com.example.practiceprefinal

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class MyApplication : Application() {
    companion object {
        private lateinit var sharedPreferences: SharedPreferences
        private val SIMPLE_STUDENTS = "simple_students"
        private val CUSTOM_STUDENTS = "custom_students"

        fun initialize(context: Context){
            sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        }

        fun setIsLoggedIn(isLoggedIn: Boolean){
            with(sharedPreferences.edit()){
                putBoolean("isLoggedIn", isLoggedIn)
                apply()
            }
        }

        fun isLoggedIn(): Boolean {
            return sharedPreferences.getBoolean("isLoggedIn", false)
        }

        fun setCustomStudents(students: List<Student>){
            with(sharedPreferences.edit()){
                putStringSet(CUSTOM_STUDENTS, students.map { "${it.id}, ${it.name}, ${it.email}, ${it.grade}" }.toSet())
                apply()
            }
        }

        fun getCustomStudents(): MutableList<Student> {
            val students = sharedPreferences.getStringSet(CUSTOM_STUDENTS, setOf())?: setOf()
            return students.map {
                val parts = it.split(",")
                Student(
                    id = parts[0].toInt(),
                    name = parts[1],
                    email = parts[2],
                    grade = parts[3].toDouble()
                )
            }.toMutableList()
        }

        fun setSimpleStudents(students: List<String>){
            with(sharedPreferences.edit()){
                putStringSet(SIMPLE_STUDENTS, students.toMutableSet())
                apply()
            }
        }

        fun getSimpleStudents(): MutableList<String>{
            val students = sharedPreferences.getStringSet(SIMPLE_STUDENTS, mutableSetOf()) ?: mutableSetOf()
            return students.toMutableList()
        }

        fun saveCredentials(username: String, password: String){
            with(sharedPreferences.edit()){
                putString("username", username)
                putString("password", password)
                apply()
            }
        }

        fun setUsername(username: String){
            with(sharedPreferences.edit()){
                putString("username", username)
                apply()
            }
        }

        fun getUsername(): String? {
            return sharedPreferences.getString("username", null)
        }

        fun setPassword(password: String){
            with(sharedPreferences.edit()){
                putString("password", password)
                apply()
            }
        }

       fun getPassword(): String? {
           return sharedPreferences.getString("password", null)
       }

        fun clearCredentials(){
            val username = getUsername()
            val password = getPassword()
            val students = getSimpleStudents()

            with(sharedPreferences.edit()){
                clear()
                username?.let { putString("username", it)}
                password?.let { putString("password", it)}
                putStringSet(SIMPLE_STUDENTS, students.toMutableSet())
                putBoolean("isLoggedIn", false)
                apply()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        initialize(this)
    }
}