package com.example.practiceprefinal

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditProfile : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val confirmPassword = findViewById<EditText>(R.id.confirmPassword)
        val saveChanges = findViewById<Button>(R.id.saveChanges)
        val back = findViewById<Button>(R.id.back)

        back.setOnClickListener {
            finish()
        }

        saveChanges.setOnClickListener {
            val usernameInput = username.text.toString()
            val passwordInput = password.text.toString()
            val confirmInput = confirmPassword.text.toString()

            if(usernameInput.isNotEmpty()){
                if(usernameInput.length < 6){
                    Toast.makeText(this, "Username must be at least 6 characters", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            }

            if(passwordInput.isNotEmpty() || confirmInput.isNotEmpty()){
                when {
                    passwordInput.isEmpty() -> {
                        Toast.makeText(this, "Password should not be empty", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    confirmInput.isEmpty() -> {
                        Toast.makeText(this, "Confirm Password should not be empty", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    confirmInput != passwordInput -> {
                        Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                }
            }

            if(usernameInput.isNotEmpty()){
                MyApplication.setUsername(usernameInput)
            }
            if(passwordInput.isNotEmpty()){
                MyApplication.setPassword(passwordInput)
            }

            Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}