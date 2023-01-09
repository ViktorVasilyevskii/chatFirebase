package com.vasilevskii.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.vasilevskii.chat.databinding.ActivityLogInBinding

class LogIn : AppCompatActivity() {

    private lateinit var bindingLogIn: ActivityLogInBinding
    private lateinit var mFirebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLogIn = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(bindingLogIn.root)

        initFirebaseAuth()

        listenerButtonSignUp()
        listenerButtonLogin()
    }

    private fun initFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance()
    }

    private fun listenerButtonSignUp() {
        bindingLogIn.buttonSingUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }

    private fun listenerButtonLogin() {
        bindingLogIn.apply {
            buttonLogIn.setOnClickListener {
                val email = editEmail.text.toString()
                val password = editPassword.text.toString()

                login(email, password)
            }
        }
    }

    private fun login(email: String, password: String) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    finish()
                    startActivity(Intent(this@LogIn, MainActivity::class.java))
                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}