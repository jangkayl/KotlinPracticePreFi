package com.example.practiceprefinal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val confirmPassword = findViewById<EditText>(R.id.confirmPassword)
        val loginButton = findViewById<TextView>(R.id.login_button)
        val registerButton = findViewById<Button>(R.id.registerNow)

        loginButton.setOnClickListener {
            finish()
        }

        registerButton.setOnClickListener {
            if(username.text.isNullOrEmpty() || password.text.isNullOrEmpty()){
                Toast.makeText(this, "Fields should not be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(username.text.length < 6 || password.text.length < 6){
                Toast.makeText(this, "Fields should have at least 6 length", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(password.text.toString() != confirmPassword.text.toString()){
                Toast.makeText(this, "Password mismatch", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            MyApplication.saveCredentials(username.text.toString(), password.text.toString())
            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}