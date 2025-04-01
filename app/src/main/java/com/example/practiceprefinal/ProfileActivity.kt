package com.example.practiceprefinal

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ProfileActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        val username = findViewById<TextView>(R.id.username)
        val password = findViewById<TextView>(R.id.password)
        val back = findViewById<Button>(R.id.back)

        username.text = MyApplication.getUsername()
        password.text = MyApplication.getPassword()

        back.setOnClickListener {
            finish()
        }
    }
}