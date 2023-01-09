package com.vasilevskii.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.vasilevskii.chat.databinding.ActivitySignUpBinding
import com.vasilevskii.chat.model.User

class SignUp : AppCompatActivity() {

    private lateinit var bindingSingUp: ActivitySignUpBinding
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingSingUp = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(bindingSingUp.root)

        supportActionBar?.hide()

        initFirebaseAuth()

        listenerButtonSingUp()
    }

    private fun listenerButtonSingUp() {
        bindingSingUp.apply {
            buttonSingUp.setOnClickListener {
                val name = editName.text.toString()
                val email = editEmail.text.toString()
                val password = editPassword.text.toString()
                singUp(name, email, password)
            }
        }
    }

    private fun initFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance()
    }

    private fun singUp(name: String, email: String, password: String) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email, mFirebaseAuth.currentUser?.uid!!)
                    finish()
                    startActivity(Intent(this@SignUp, MainActivity::class.java))
                } else {
                    Toast.makeText(this@SignUp, "Authentication failed.", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDatabaseReference = FirebaseDatabase.getInstance().reference

        mDatabaseReference.child("user").child(uid).setValue(User(name, email, uid))
    }
}