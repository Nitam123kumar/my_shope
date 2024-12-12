package com.example.myshope

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var email: EditText
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        email = findViewById(R.id.email_login)
        password = findViewById(R.id.password_login)
        val SignUpPage = findViewById<TextView>(R.id.SignUpPage)
        SignUpPage.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        val login_btn = findViewById<AppCompatButton>(R.id.login_btn)
        login_btn.setOnClickListener {

            val email1 = email.text.toString().trim()
            val password1 = password.text.toString().trim()

            if (email1.isEmpty()) {
                email.error = "required this email"
            } else if (!isGmail(email1)) {
                email.error = "Only Gmail accounts are allowed"
            } else if (password1.isEmpty()) {
                password.error = "required this password"
            }
            else if (password1.length < 8) {
                Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
            }else {

                loginWithEmailAndPassword(email1, password1)
            }
        }

    }

    private fun loginWithEmailAndPassword(email2: String, password2: String) {
        auth.signInWithEmailAndPassword(email2, password2)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()

                email.text.clear()
                password.text.clear()

            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "Login Failed: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }


    }

    private fun isGmail(email: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9._%+-]+@gmail\\.com$")
        return regex.matches(email)
    }


}