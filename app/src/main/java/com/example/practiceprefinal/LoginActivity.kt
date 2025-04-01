package com.example.practiceprefinal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val register = findViewById<TextView>(R.id.register_button)
        val loginButton = findViewById<Button>(R.id.loginNow)

        if(MyApplication.isLoggedIn()){
            startActivity(Intent(this, HomepageActivity::class.java))
            finish()
        }

        username.setText(MyApplication.getUsername())

        register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        loginButton.setOnClickListener {
            val savedUsername = MyApplication.getUsername()
            val savedPassword = MyApplication.getPassword()

            if(username.text.isNullOrEmpty() || password.text.isNullOrEmpty()){
                Toast.makeText(this, "Fields should not be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(username.text.toString() == savedUsername && password.text.toString() == savedPassword){
                MyApplication.setIsLoggedIn(true)
                startActivity(Intent(this, HomepageActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                finish()
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }
    }
}