package com.example.practiceprefinal

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class HomepageActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        val profileButton = findViewById<Button>(R.id.seeProfile)
        val editButton = findViewById<Button>(R.id.editProfile)
        val logoutButton = findViewById<Button>(R.id.logout)
        val listViewButton = findViewById<Button>(R.id.list_view)
        val customViewButton = findViewById<Button>(R.id.custom_listView)

        profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        editButton.setOnClickListener {
            startActivity(Intent(this, EditProfile::class.java))
        }

        editButton.setOnClickListener {
            startActivity(Intent(this, EditProfile::class.java))
        }

        listViewButton.setOnClickListener {
            startActivity(Intent(this, ListViewActivity::class.java))
        }

        customViewButton.setOnClickListener {
            startActivity(Intent(this, CustomListView::class.java))
        }

        logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") {
                    _, _ ->
                MyApplication.setIsLoggedIn(false)
                MyApplication.clearCredentials()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }.setNegativeButton("No") {
                dialog, _ ->
                dialog.dismiss()
            }.setIcon(android.R.drawable.ic_dialog_alert).show()
    }
}